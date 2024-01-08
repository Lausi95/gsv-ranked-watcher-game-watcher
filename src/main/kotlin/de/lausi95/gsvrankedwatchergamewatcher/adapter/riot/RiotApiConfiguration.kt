package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class RiotApiConfiguration(@Value("\${riot.api-key}") val apiKey: String) {

  @Bean
  fun riotAdapter(): RiotAdapter {
    val summonerRestTemplate = RestTemplateBuilder()
      .defaultHeader("X-Riot-Token", apiKey)
      .rootUri("https://europe.api.riotgames.com")
      .build()
    val matchRestTemplate = RestTemplateBuilder()
      .defaultHeader("X-Riot-Token", apiKey)
      .rootUri("https://europe.api.riotgames.com")
      .build()

    return RiotAdapter(summonerRestTemplate, matchRestTemplate)
  }
}
