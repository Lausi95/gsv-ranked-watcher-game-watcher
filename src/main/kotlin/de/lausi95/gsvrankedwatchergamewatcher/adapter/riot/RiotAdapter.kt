package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import org.springframework.web.client.RestTemplate

data class MatchDto(
  val metadata: MetadataDto,
  val info: InfoDto
)

data class MetadataDto(
  val matchId: String,
)

data class InfoDto(
  val gameDuration: Long,
  val gameEndTimestamp: Long? = null,
  val gameStartTimestamp: Long,
  val participants: List<ParticipantDto>,
  val queueId: Int,
)

data class ParticipantDto(
  val win: Boolean,
  val summonerId: String,
  val summonerName: String,
  val championName: String,
  val role: String,
  val kills: Int,
  val deaths: Int,
  val assists: Int,
  val pentaKills: Int,
  val quadraKills: Int
)

internal class RiotAdapter(
  private val restTemplate: RestTemplate,
) {

  fun getLatestMatchIds(summonerId: String, count: Int = 1): List<String> {
    val url = "/lol/match/v5/matches/by-puuid/$summonerId/ids?count=$count"
    val matchIds = restTemplate.getForObject(url, Array<String>::class.java)
    return matchIds?.toList()!!
  }

  fun getMatch(matchId: String): MatchDto {
    val url = "/lol/match/v5/matches/$matchId"
    val match = restTemplate.getForObject(url, MatchDto::class.java)
    return match!!
  }
}
