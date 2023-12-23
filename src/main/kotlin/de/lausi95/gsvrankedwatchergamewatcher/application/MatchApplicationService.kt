package de.lausi95.gsvrankedwatchergamewatcher.application

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchRepository
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.stereotype.Component

@Component
class MatchApplicationService(
  val matchCrawler: MatchCrawler,
  val matchRepository: MatchRepository,
  val playerRepository: PlayerRepository,
  val matchReporter: MatchReporter
) {

  fun crawlMatches() {
    val summonerIds = playerRepository.getPlayers().map { it.summonerId }
    matchCrawler.crawlMatches(summonerIds, { !matchRepository.existsById(it) }) {
      matchReporter.reportMatch(it)
      matchRepository.save(it)
    }
  }
}
