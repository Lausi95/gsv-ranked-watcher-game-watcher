package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.*
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

private val QUEUE_IDS = listOf(420, 440)

@Component
private class RiotMatchCrawler(private val riotAdapter: RiotAdapter) : MatchCrawler {

  private val log = LoggerFactory.getLogger(RiotMatchCrawler::class.java)

  override fun crawlMatches(summonerIds: List<SummonerId>, predicate: MatchPredicate, matchCallback: MatchCallback) {
    summonerIds
      .flatMap { riotAdapter.getLatestMatchIds(it) }
      .distinct()
      .forEach { processMatch(it, summonerIds, predicate, matchCallback) }
  }

  private fun processMatch(matchId: String, summonerIds: List<SummonerId>, predicate: MatchPredicate, matchCallback: MatchCallback) {
    log.info("Processing match '${matchId}'.")

    if (!predicate(matchId)) {
      log.info("Not processing match $matchId. Already processed.")
      return
    }

    val matchDto = riotAdapter.getMatch(matchId)
    if (!isRankedMatch(matchDto.info.queueId)) {
      log.info("Not reporting match $matchId. Not a ranked match.")
      return
    }

    matchCallback(matchDto.mapToMatch(summonerIds))
  }
}

fun isRankedMatch(queueId: Int): Boolean {
  return QUEUE_IDS.contains(queueId)
}

fun MatchDto.mapToMatch(summonerIds: List<SummonerId>): Match {
  val summonerIdValues = summonerIds.map { it.value }
  val participantDtos = info.participants.filter {
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

  return Match(metadata.matchId, win, participants)
}
