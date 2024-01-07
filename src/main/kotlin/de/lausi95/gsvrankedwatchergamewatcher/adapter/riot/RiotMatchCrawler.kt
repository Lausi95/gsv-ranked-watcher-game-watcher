package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import com.github.kimcore.riot.RiotAPI
import com.github.kimcore.riot.routes.MatchDto
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Participant
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
private class RiotMatchCrawler : MatchCrawler {

  companion object {
    private val QUEUE_IDS = listOf(420, 440)
  }

  override fun crawlMatches(summonerIds: List<String>, shouldReportMatch: (matchId: String) -> Boolean, reportMatch: (Match) -> Unit) {
    runBlocking {
      coroutineScope {
        val matchIds = summonerIds.map { summonerId -> async { RiotAPI.match.getMatchIdsByPUUID(summonerId) } }
          .flatMap { it.await() }
          .distinct()

        matchIds.forEach { matchId ->
          launch {
            if (!shouldReportMatch(matchId)) {
              return@launch
            }

            val riotMatch = RiotAPI.match.getMatch(matchId)
            if (!QUEUE_IDS.contains(riotMatch.info.queueId)) {
              return@launch
            }

            val match: Match = mapToMatch(riotMatch, summonerIds)
            reportMatch(match)
          }
        }
      }
    }
  }

  fun mapToMatch(matchDto: MatchDto, summonerIds: List<String>): Match {
    val participantDtos = matchDto.info.participants.filter {
      summonerIds.contains(it.summonerId)
    }

    val win = participantDtos.any { it.win }

    val participants = participantDtos.map {
      Participant(
        it.summonerId,
        it.summonerName,
        it.championName,
        it.role,
        it.kills,
        it.deaths,
        it.assists,
        it.pentaKills > 0,
        it.quadraKills > 0
      )
    }

    return Match(matchDto.metadata.matchId, win, participants)
  }
}
