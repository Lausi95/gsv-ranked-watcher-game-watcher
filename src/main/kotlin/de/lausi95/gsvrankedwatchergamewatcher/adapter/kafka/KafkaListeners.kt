package de.lausi95.gsvrankedwatchergamewatcher.adapter.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gsvrankedwatchergamewatcher.application.PlayerApplicationService
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
private class KafkaListeners(
  val objectMapper: ObjectMapper,
  val playerApplicationService: PlayerApplicationService) {

  @KafkaListener(topics = [PLAYERS_UPDATED_TOPIC])
  fun handlePlayersUpdated(message: String) {
    val playersUpdatedMessage = objectMapper.readValue(message, PlayersUpdatedMessage::class.java)
    val players = playersUpdatedMessage.summonerIds.map { Player(it) }
    playerApplicationService.updatePlayers(players)
  }
}
