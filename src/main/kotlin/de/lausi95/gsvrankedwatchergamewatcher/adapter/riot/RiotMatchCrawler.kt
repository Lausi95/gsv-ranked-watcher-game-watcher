package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Participant
import org.springframework.stereotype.Component

@Component
private class RiotMatchCrawler(private val riotAdapter: RiotAdapter) : MatchCrawler {

  companion object {
    private val QUEUE_IDS = listOf(420, 440)
  }

  override fun crawlMatches(summonerIds: List<String>, shouldReportMatch: (matchId: String) -> Boolean, reportMatch: (Match) -> Unit) {
    summonerIds.flatMap { summonerId -> riotAdapter.getLatestMatchIds(summonerId) }.distinct().forEach { matchId ->
      if (!shouldReportMatch(matchId)) {
        return
      }

      val riotMatch = riotAdapter.getMatch(matchId)
      if (!QUEUE_IDS.contains(riotMatch.info.queueId)) {
        return
      }

      val match: Match = mapToMatch(riotMatch, summonerIds)
      reportMatch(match)
    }
  }

  fun mapToMatch(matchDto: MatchDto, summonerIds: List<String>): Match {
    val participantDtos = matchDto.info.participants.filter {
      summonerIds.contains(it.puuid)
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

    return Match(matchDto.info.gameId.toString(), win, participants)
  }
}
