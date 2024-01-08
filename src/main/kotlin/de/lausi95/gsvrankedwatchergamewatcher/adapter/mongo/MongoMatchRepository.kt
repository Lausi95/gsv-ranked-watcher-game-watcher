package de.lausi95.gsvrankedwatchergamewatcher.adapter.mongo

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.match.MatchRepository
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

private data class MatchEntity(@Id val id: String)

private interface InternalMongoRepository: MongoRepository<MatchEntity, String>

@Component
private class MongoMatchRepository(private val repository: InternalMongoRepository) : MatchRepository {

  override fun existsById(matchId: String): Boolean {
    return repository.existsById(matchId)
  }

  override fun save(matchId: String) {
    val matchEntity = MatchEntity(matchId)
    repository.save(matchEntity)
  }
}
