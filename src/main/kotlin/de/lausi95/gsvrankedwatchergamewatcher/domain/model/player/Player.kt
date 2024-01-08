package de.lausi95.gsvrankedwatchergamewatcher.domain.model.player

data class SummonerId(val value: String)

data class SummonerName(val value: String)

data class Player(val summonerId: SummonerId, val summonerName: SummonerName)
