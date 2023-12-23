package de.lausi95.gsvrankedwatchergamewatcher.domain.model.match

data class Participant(
  val summonerId: String,
  val summonerName: String,
  val champion: String,
  val position: String,
  val kills: Int,
  val deaths: Int,
  val assists: Int,
  val pentaKill: Boolean,
  val quadraKill: Boolean
)

data class Match(
  val matchId: String,
  val win: Boolean,
  val participants: List<Participant>
)
