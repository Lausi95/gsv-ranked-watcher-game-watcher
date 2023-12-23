package de.lausi95.gsvrankedwatchergamewatcher.adapter.riot

import com.github.kimcore.riot.RiotAPI
import com.github.kimcore.riot.enums.Platform
import com.github.kimcore.riot.enums.Region
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
private class RiotApiConfiguration(@Value("\${riot.api-key}") val apiKey: String) {

  @EventListener(ApplicationStartedEvent::class)
  fun setUpRiotApi() {
    RiotAPI.setApiKey(apiKey)
    RiotAPI.setDefaultRegion(Region.EUROPE)
    RiotAPI.setDefaultPlatform(Platform.EUW1)
  }
}
