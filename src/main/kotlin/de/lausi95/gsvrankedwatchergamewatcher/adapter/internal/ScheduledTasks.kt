package de.lausi95.gsvrankedwatchergamewatcher.adapter.internal

import de.lausi95.gsvrankedwatchergamewatcher.application.MatchApplicationService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
private class ScheduledTasks(val matchApplicationService: MatchApplicationService) {

  @Scheduled(fixedDelay = 5, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
  fun crawlMatches() {
    matchApplicationService.crawlMatches()
  }
}
