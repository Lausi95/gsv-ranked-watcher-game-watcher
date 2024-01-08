package de.lausi95.gsvrankedwatchergamewatcher.adapter.discordreporter

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate

internal class DiscordReporterAdapter(private val restTemplate: RestTemplate) : MatchReporter {

  private val log = LoggerFactory.getLogger(DiscordReporterAdapter::class.java)

  override fun reportMatch(match: Match) {
    log.info("Reporting match '${match.matchId}' to discord-reporter.")

    restTemplate.postForEntity("/matches", match, Any::class.java)
  }
}
