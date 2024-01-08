package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class RiotApiConfiguration {

  @Bean
  fun riotAdapter(@Value("\${riot.api-key}") apiKey: String): RiotAdapter {
    val restTemplate = RestTemplateBuilder()
      .defaultHeader("X-Riot-Token", apiKey)
      .rootUri("https://europe.api.riotgames.com")
      .build()

    return RiotAdapter(restTemplate)
  }
}
