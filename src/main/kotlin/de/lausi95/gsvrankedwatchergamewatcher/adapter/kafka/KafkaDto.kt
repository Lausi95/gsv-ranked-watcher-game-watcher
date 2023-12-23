package de.lausi95.gsvrankedwatchergamewatcher.adapter.kafka

const val SERVICE_UP_TOPIC = "service_up"
internal data class ServiceUpMessage(
  val requiredData: List<String>
)

const val PLAYERS_UPDATED_TOPIC = "players_updated"
internal data class PlayersUpdatedMessage(
  val summonerIds: List<String>
)

const val MATCH_PLAYED_TOPIC = "match_played"
internal data class MatchPlayedMessage(
  val matchId: String
)
