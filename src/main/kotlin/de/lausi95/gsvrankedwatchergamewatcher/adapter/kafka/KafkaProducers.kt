package de.lausi95.gsvrankedwatchergamewatcher.adapter.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchReporter
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
private class KafkaProducers(val kafkaTemplate: KafkaTemplate<String, String>, val objectMapper: ObjectMapper) : MatchReporter {

  @EventListener(ApplicationStartedEvent::class)
  fun broadcastServiceUp() {
    val serviceUpMessage = ServiceUpMessage(listOf("players"))
    kafkaTemplate.send(SERVICE_UP_TOPIC, objectMapper.writeValueAsString(serviceUpMessage))
  }

  override fun reportMatch(match: Match) {
    TODO("Properly map the match to a DTO")
    val matchPlayedMessage = MatchPlayedMessage(match.matchId)
    kafkaTemplate.send(MATCH_PLAYED_TOPIC, objectMapper.writeValueAsString(matchPlayedMessage))
  }
}
