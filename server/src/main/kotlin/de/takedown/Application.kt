package de.takedown

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.htmlunit.BrowserVersion
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                // Hier mein Code, TEST
                /*val webClient = setupWebClient()

                val link = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=2023&land=DE&ZB=E"
                val newHtml: HtmlPage = webClient.getPage(link)
                println(fetchTable(newHtml))*/

                //call.respondText("Hello, world!")
            }
        }
    }.start(wait = true)
}


data class Tournament(
    val id: String,
    val name: String,

    val date: String,
    val isFinished: Boolean,

    val country: String,
    val city: String,
    val venue: String,

    val host: String,
    val club: String,

    val ageWeightClasses: List<String> = emptyList(),
    val rankings: List<String> = emptyList()

    // Anzahl Teilnehmer, Teilnehmer, Resultate
)

fun setupWebClient(): WebClient {
    val webClient = WebClient(BrowserVersion.CHROME)
    webClient.options.isThrowExceptionOnScriptError = false
    webClient.options.isCssEnabled = false
    webClient.javaScriptTimeout = 120000

    return webClient
}

private fun fetchTable(page: HtmlPage): List<Tournament> {
    val list = mutableListOf<Tournament>()
    // Table finden und Rows in Liste, dann über Rows iterieren
    val tableBody = page.getFirstByXPath<HtmlTableBody>("//table[@class='rgMasterTable']/tbody")
    val rows: List<HtmlTableRow> = tableBody.rows

    rows.forEach { row ->
        val cells: List<HtmlTableCell> = row.cells

        val date = cells[0].asNormalizedText()
        val name = cells[1].asNormalizedText()
        val venue = cells[2].asNormalizedText()
        val host = cells[3].asNormalizedText()
        val club = cells[4].asNormalizedText()
        val detailsLink = cells[5].getFirstByXPath<HtmlAnchor>(".//a").getAttribute("href")

        val id = detailsLink.replace("TurnierDetailInfo.aspx?TID=", "")
        //val turnierDatum = parseDate(date)

        val turnier = Tournament(
            id = id,
            date = date,
            isFinished = false,
            name = name,
            country = "TEST",
            city = "TEST",
            venue = venue,
            host = host,
            club = club
        )

        list.add((turnier))
    }

    return list
}
