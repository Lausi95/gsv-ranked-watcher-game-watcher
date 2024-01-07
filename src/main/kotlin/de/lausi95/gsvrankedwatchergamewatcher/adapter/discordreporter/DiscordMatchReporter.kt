package de.lausi95.gsvrankedwatchergamewatcher.adapter.discordreporter

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import org.springframework.web.client.RestTemplate

internal class DiscordMatchReporter(private val restTemplate: RestTemplate) : MatchReporter {

  override fun reportMatch(match: Match) {
    restTemplate.postForEntity("/matches", match, Any::class.java)
  }
}
