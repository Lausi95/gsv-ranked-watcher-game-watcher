package de.lausi95.gsvrankedwatchergamewatcher.adapter.playermanager

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
private class PlayerManagerConfiguration {

  @Bean
  fun playerRepository(@Value("\${player-manager.host}") playerManagerHost: String): PlayerRepository {
    val restTemplate = RestTemplateBuilder().rootUri(playerManagerHost).build()
    return PlayerManagerPlayerRepository(restTemplate)
  }
}
