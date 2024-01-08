package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class RiotApiConfiguration(@Value("\${riot.api-key}") val apiKey: String) {

  @Bean
  fun riotAdapter(objectMapper: ObjectMapper): RiotAdapter {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    val restTemplate = RestTemplateBuilder()
      .defaultHeader("X-Riot-Token", apiKey)
      .rootUri("https://europe.api.riotgames.com")
      .build()

    return RiotAdapter(restTemplate)
  }
}
