package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import com.github.kimcore.riot.RiotAPI
import com.github.kimcore.riot.routes.MatchDto
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Participant
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
private class RiotMatchCrawler : MatchCrawler {

  companion object {
    private val QUEUE_IDS = listOf(420, 440)
  }

  override fun crawlMatches(summonerIds: List<String>, shouldReportMatch: (matchId: String) -> Boolean, reportMatch: (Match) -> Unit) {
    runBlocking {
      summonerIds.asSequence()
        .flatMap { runBlocking { RiotAPI.match.getMatchIdsByPUUID(it, count = 1) } }
        .distinct()
        .filter(shouldReportMatch)
        .map { runBlocking { RiotAPI.match.getMatch(it) } }
        .filter { QUEUE_IDS.contains(it.info.queueId) }
        .map { mapToMatch(it, summonerIds) }.toList()
        .forEach(reportMatch)
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