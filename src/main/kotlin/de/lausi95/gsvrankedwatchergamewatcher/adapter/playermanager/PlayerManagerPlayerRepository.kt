package de.lausi95.gsvrankedwatchergamewatcher.adapter.playermanager

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import org.springframework.web.client.RestTemplate

private data class PlayerCollection(
  val players: List<Player>
)

internal class PlayerManagerPlayerRepository(private val restTemplate: RestTemplate): PlayerRepository {

  override fun getPlayers(): List<Player> {
    val response = restTemplate.getForEntity("/players", PlayerCollection::class.java)
    return response.body?.players!!
  }
}
