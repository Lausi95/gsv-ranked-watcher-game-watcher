package de.lausi95.gsvrankedwatchergamewatcher.adapter.playermanager

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.Player
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.PlayerRepository
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerId
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerName
import org.slf4j.LoggerFactory
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

internal class PlayerManagerAdapter(private val restTemplate: RestTemplate): PlayerRepository {

  private val log = LoggerFactory.getLogger(PlayerManagerAdapter::class.java)

  override fun getPlayers(): List<Player> {
    val response = restTemplate.getForEntity("/players", PlayerCollection::class.java)
    val players = requireNotNull(response.body?.players?.map { it.toPlayer() })

    log.info("Loaded ${players.size} from player-manager.")

    return players
  }
}
