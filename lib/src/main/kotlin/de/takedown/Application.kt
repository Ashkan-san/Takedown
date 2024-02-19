package de.takedown

import org.htmlunit.html.HtmlPage
import ui.tournaments.TournamentRepository
import kotlin.system.measureTimeMillis

val webClient = setupWebClient()
val realmRepo = TournamentRepository()

fun main() {
    val test = "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=23417"
    val page: HtmlPage = webClient.getPage(test)

    scrapeTournament(page)

    val executionTime = measureTimeMillis {
        //scrapeAllTournaments()
    }

    println("EXECUTION TIME IN SEK COROUTINE: ${executionTime}")
    webClient.close()
    //println("EXECUTION TIME IN SEK: ${}")
}