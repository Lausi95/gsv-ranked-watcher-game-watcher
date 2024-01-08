package de.lausi95.gsvrankedwatchergamewatcher.application

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchCrawler
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchRepository
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.stereotype.Component

@Component
class MatchApplicationService(
  private val matchCrawler: MatchCrawler,
  private val matchRepository: MatchRepository,
  private val playerRepository: PlayerRepository,
  private val matchReporter: MatchReporter,
) {

  fun crawlMatches() {
    val summonerIds = playerRepository.getPlayers().map { it.summonerId }
    matchCrawler.crawlMatches(summonerIds, ::shouldProcessMatch) {
      matchReporter.reportMatch(it)
    }
  }

  private fun shouldProcessMatch(matchId: String): Boolean {
    if (matchRepository.existsById(matchId)) {
      return false
    }

    matchRepository.save(matchId)
    return true
  }
}
