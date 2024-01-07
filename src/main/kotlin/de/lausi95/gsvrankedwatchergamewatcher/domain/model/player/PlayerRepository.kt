package de.lausi95.gsvrankedwatchergamewatcher.domain.model.player

interface PlayerRepository {

  fun getPlayers(): List<Player>
}
