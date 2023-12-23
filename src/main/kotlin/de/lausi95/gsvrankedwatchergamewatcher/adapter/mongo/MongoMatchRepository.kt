package de.lausi95.gsvrankedwatchergamewatcher.adapter.mongo

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.Match
import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchRepository
import org.springframework.stereotype.Component

@Component
private class MongoMatchRepository : MatchRepository {

  override fun existsById(matchId: String): Boolean {
    TODO("implement this")
  }

  override fun save(match: Match) {
    TODO("implement this")
  }
}
