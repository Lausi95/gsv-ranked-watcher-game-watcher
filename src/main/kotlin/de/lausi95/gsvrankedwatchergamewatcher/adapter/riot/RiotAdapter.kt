package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import com.github.kimcore.riot.routes.MatchDto
import org.springframework.web.client.RestTemplate

internal class RiotAdapter(
  private val summonerRestTemplate: RestTemplate,
  private val matchRestTemplate: RestTemplate,
) {

  fun getLatestMatchIds(summonerId: String, count: Int = 1): List<String> {
    val url = "/lol/match/v5/matches/by-puuid/$summonerId/ids?count=$count"
    val matchIds = summonerRestTemplate.getForObject(url, Array<String>::class.java)
    return matchIds?.toList()!!
  }

  fun getMatch(matchId: String): MatchDto {
    val url = "/lol/match/v5/matches/$matchId"
    val match = matchRestTemplate.getForObject(url, MatchDto::class.java)
    return match!!
  }
}
