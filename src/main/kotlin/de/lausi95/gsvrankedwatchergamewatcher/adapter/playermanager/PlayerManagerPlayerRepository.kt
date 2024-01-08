package de.lausi95.gsvrankedwatchergamewatcher.adapter.playermanager

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerId
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerName
import org.springframework.web.client.RestTemplate

private data class PlayerRessource(
  val summonerId: String,
  val summonerName: String,
) {

  fun toPlayer(): Player = Player(
    SummonerId(summonerId),
    SummonerName(summonerName),
  )
}

private data class PlayerCollection(
  val players: List<PlayerRessource>
)

internal class PlayerManagerPlayerRepository(private val restTemplate: RestTemplate): PlayerRepository {

  override fun getPlayers(): List<Player> {
    val response = restTemplate.getForEntity("/players", PlayerCollection::class.java)
    return response.body?.players?.map { it.toPlayer() }!!
  }
}
