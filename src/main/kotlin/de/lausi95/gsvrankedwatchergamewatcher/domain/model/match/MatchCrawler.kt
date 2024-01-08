package de.lausi95.gsvrankedwatchergamewatcher.domain.model.match

import de.lausi95.gsvrankedwatchergamewatcher.domain.model.player.SummonerId

typealias MatchPredicate = (matchId: String) -> Boolean
typealias MatchCallback = (match: Match) -> Unit

interface MatchCrawler {

  /**
   * Crawls matches for a list of summonerIds based on a given predicate,
   * and invokes a matchCallback for each match.
   *
   * @param summonerIds the list of summonerIds for which matches should be crawled
   * @param predicate the match predicate that determines if a match should be processed or not
   * @param matchCallback the callback function to be invoked for each match that fulfills the predicate
   */
  fun crawlMatches(summonerIds: List<SummonerId>, predicate: MatchPredicate, matchCallback: MatchCallback)
}
