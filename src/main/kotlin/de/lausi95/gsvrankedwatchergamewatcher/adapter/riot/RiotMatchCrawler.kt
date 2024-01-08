package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Participant
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
private class RiotMatchCrawler(private val riotAdapter: RiotAdapter) : MatchCrawler {

  companion object {
    private val QUEUE_IDS = listOf(420, 440)
  }

  private val log = LoggerFactory.getLogger(RiotMatchCrawler::class.java)

  override fun crawlMatches(summonerIds: List<SummonerId>, shouldReportMatch: (matchId: String) -> Boolean, reportMatch: (Match) -> Unit) {
    summonerIds.flatMap { summonerId -> riotAdapter.getLatestMatchIds(summonerId) }.distinct().forEach { matchId ->
      if (!shouldReportMatch(matchId)) {
        log.info("Not Reporting match $matchId. Already reported.")
        return@forEach
      }

      val riotMatch = riotAdapter.getMatch(matchId)
      if (!QUEUE_IDS.contains(riotMatch.info.queueId)) {
        log.info("Not reporting match $matchId. Not a ranked match.")
        return@forEach
      }

      val match: Match = mapToMatch(matchId, riotMatch, summonerIds)
      reportMatch(match)
    }
  }

  fun mapToMatch(matchId: String, matchDto: MatchDto, summonerIds: List<SummonerId>): Match {
    val summonerIdValues = summonerIds.map { it.value }
    val participantDtos = matchDto.info.participants.filter {
      summonerIdValues.contains(it.puuid)
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

    return Match(matchId, win, participants)
  }
}
