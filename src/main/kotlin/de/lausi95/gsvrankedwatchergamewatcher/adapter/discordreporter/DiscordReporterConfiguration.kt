package de.lausi95.gsvrankedwatchergamewatcher.adapter.discordreporter

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class DiscordReporterConfiguration {

  @Bean
  fun matchReporter(@Value("\${discord-reporter.host}") matchReporterHost: String): MatchReporter {
    val restTemplate = RestTemplateBuilder().rootUri(matchReporterHost).build()
    return DiscordMatchReporter(restTemplate)
  }
}
