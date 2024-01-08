package de.lausi95.gsvrankedwatchergamewatcher.domain.model.match

interface MatchRepository {

  fun existsById(matchId: String): Boolean

  fun save(matchId: String)
}
