package de.lausi95.gsvrankedwatchergamewatcher.application

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.stereotype.Component

@Component
class PlayerApplicationService(val playerRepository: PlayerRepository) {

  fun updatePlayers(players: List<Player>) {
    playerRepository.savePlayers(players)
  }
}
