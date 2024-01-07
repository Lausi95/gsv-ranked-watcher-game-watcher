package de.lausi95.gsvrankedwatchergamewatcher.application

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchRepository
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MatchApplicationService(
  private val matchCrawler: MatchCrawler,
  private val matchRepository: MatchRepository,
  private val playerRepository: PlayerRepository,
  private val matchReporter: MatchReporter
) {

  private val log = LoggerFactory.getLogger(MatchApplicationService::class.java)

  fun crawlMatches() {
    val summonerIds = playerRepository.getPlayers().map { it.summonerId }
    log.info("Reporting matches for summoner ids: $summonerIds")

    matchCrawler.crawlMatches(summonerIds, { !matchRepository.existsById(it) }) {
      matchReporter.reportMatch(it)
      matchRepository.save(it)
    }
  }
}
