package de.lausi95.gsvrankedwatchergamewatcher.domain.model.match

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerId

interface MatchCrawler {

  fun crawlMatches(
    summonerIds: List<SummonerId>,
    shouldReportMatch: (matchId: String) -> Boolean,
    reportMatch: (Match) -> Unit)
}
