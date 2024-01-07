package de.lausi95.gsvrankedwatchergamewatcher.adapter.playermanager

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.web.client.RestTemplate

internal class PlayerManagerPlayerRepository(private val restTemplate: RestTemplate): PlayerRepository {

  override fun getPlayers(): List<Player> {
    val response = restTemplate.getForEntity("/players", Array<Player>::class.java)
    return response.body?.toList()!!
  }
}
