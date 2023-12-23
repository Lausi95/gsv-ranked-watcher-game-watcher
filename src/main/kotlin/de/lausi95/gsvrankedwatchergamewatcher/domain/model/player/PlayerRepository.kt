package de.lausi95.gsvrankedwatchergamewatcher.domain.model.player

interface PlayerRepository {

  fun savePlayers(players: List<Player>)

  fun getPlayers(): List<Player>
}
