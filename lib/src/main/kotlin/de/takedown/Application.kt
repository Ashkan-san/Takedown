package de.takedown

import org.htmlunit.html.HtmlPage
import ui.tournaments.TournamentRepository
import kotlin.system.measureTimeMillis

val webClient = setupWebClient()
val realmRepo = TournamentRepository()

fun main() {
    val test = "https://www.ringerdb.de/de/turniere/Turnieruebersicht.aspx?Saison=2024&land=DE&ZB=E"
    val page: HtmlPage = webClient.getPage(test)

    //scrapeTournament(page)

    val executionTime = measureTimeMillis {
        //scrapeAllTournaments()
    }

    println("EXECUTION TIME IN SEK COROUTINE: ${executionTime}")
    //println("EXECUTION TIME IN SEK: ${}")
}