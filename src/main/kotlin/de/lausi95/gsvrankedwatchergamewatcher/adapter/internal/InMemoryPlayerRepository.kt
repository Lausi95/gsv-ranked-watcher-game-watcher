package de.lausi95.gsvrankedwatchergamewatcher.adapter.internal

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.stereotype.Component

@Component
private class InMemoryPlayerRepository : PlayerRepository {

  private val players: MutableList<Player> = mutableListOf()

  override fun savePlayers(players: List<Player>) {
    this.players.clear()
    this.players.addAll(players)
  }

  override fun getPlayers(): List<Player> {
    return this.players
  }
}
