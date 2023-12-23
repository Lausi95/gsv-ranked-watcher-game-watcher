package de.lausi95.gsvrankedwatchergamewatcher.domain.model.match

interface MatchCrawler {

  fun crawlMatches(
    summonerIds: List<String>,
    shouldReportMatch: (matchId: String) -> Boolean,
    reportMatch: (Match) -> Unit)
}
