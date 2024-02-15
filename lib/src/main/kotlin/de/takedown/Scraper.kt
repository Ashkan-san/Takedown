package de.takedown

import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.ext.toRealmSet
import model.tournament.Ranking
import model.tournament.Tournament
import model.tournament.WrestleClass
import org.htmlunit.FailingHttpStatusCodeException
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlDivision
import org.htmlunit.html.HtmlListItem
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSpan
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow
import kotlin.system.exitProcess

var countryCode: String = ""
val breakBoolean: Boolean = false
//val coroutineScope = CoroutineScope(Dispatchers.Default)

fun scrapeAllTournaments() {
    var link = getTournamentLink("2024", "DE", "E")
    var page: HtmlPage = webClient.getPage(link)

    val pastUpList = scrapePastUpcoming(page)
    val countryList = scrapeCountries(page)
    //val countryList = listOf("DK")
    var yearList: List<String>

    pastUpList.forEach { pastUp ->
        countryList.forEach { country ->
            // Ländercode für das Turnier
            countryCode = country

            // Die Jahre je nach Land ermitteln (Performance)
            link = getTournamentLink("2024", country, pastUp)
            page = webClient.getPage(link)
            yearList = scrapeYears(page)

            yearList.forEach { year ->
                val newLink = getTournamentLink(year, country, pastUp)
                val newPage: HtmlPage = webClient.getPage(newLink)

                scrapeAllPages(newPage)
            }
        }
    }

    webClient.close()
}

fun scrapeAllPages(page: HtmlPage) {
    try {
        var nextPage = page
        var nextPageInput = page.getFirstByXPath<HtmlSubmitInput>("//input[@class='rgPageNext']")

        if (nextPageInput != null) {
            val pagesDiv = page.getFirstByXPath<HtmlDivision>("//div[@class='rgWrap rgNumPart']")
            val pageCount = pagesDiv.childElementCount

            // Solange es noch weitere Seiten gibt
            repeat(pageCount) {
                scrapeTable(nextPage)

                nextPage = nextPageInput.click()
                nextPageInput = nextPage.getFirstByXPath("//input[@class='rgPageNext']")
            }
        } else {
            scrapeTable(nextPage)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        debugExit()
    }
}


fun scrapeTable(page: HtmlPage) {
    try {
        val tableBody = page.getFirstByXPath<HtmlTableBody>("//table[@id='ctl00_ContentPlaceHolderInhalt_dgrTurnieruebersicht_ctl00']/tbody")
        val rows: List<HtmlTableRow> = tableBody.rows

        rows.forEach { row ->
            if (checkTableEmpty(row)) {
                return
            }

            val cells: List<HtmlTableCell> = row.cells
            val tournamentLink = cells[5].getFirstByXPath<HtmlAnchor>(".//a").getAttribute("href")
            val id = getTournamentId(tournamentLink)
            val detailsLink = "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=$id"
            val detailsPage: HtmlPage = webClient.getPage(detailsLink)

            scrapeTournament(detailsPage)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        debugExit()
    }
}

fun scrapeTournament(page: HtmlPage) {
    val tournament = Tournament().apply {
        this.id = getTournamentId(page.url.toString())
    }

    try {
        val name = page.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtTurnierBezeichnung']")
        val date = page.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtDatum']").textContent
        val city = page.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtOrt']").textContent
        val venue = page.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtWettkampfstaette']").textContent
        val club = page.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtAusrichter']").textContent
        val host = page.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtVeranstalter']").textContent
        val wrestleClasses = scrapeWrestleClasses(page).toRealmList()
        var rankings = listOf<Ranking>()

        val resultLink = page.getFirstByXPath<HtmlAnchor>("//a[@id='ctl00_ContentPlaceHolderInhalt_lnkZuDenErgebnisse']")
        if (resultLink != null) {
            if (resultLink.hasAttribute("href")) {
                val resultLinkString = resultLink.getAttribute("href")
                val resultsPage: HtmlPage = webClient.getPage(resultLinkString)
                rankings = scrapeRankings(resultsPage, resultLinkString)
            }
        }
        val wrestlerCount = rankings.size
        val parsedDate = parseDate(date)

        tournament.apply {
            this.name = name.textContent
            this.date = parsedDate
            this.status = setTournamentStatus(parsedDate)
            this.city = city
            this.country = countryCode
            this.venue = venue
            this.club = club
            this.host = host
            this.wrestlerCount = wrestlerCount

            this.wrestleClasses = wrestleClasses
            this.rankings = rankings.toRealmList()
        }

    } catch (e: Exception) {
        // Selten kann der Parser die Seite nicht lesen, aber wenn er es einzeln macht, geht es??
        e.printStackTrace()
        debugExit()
    }

    realmRepo.addTournament(tournament)
}

fun scrapeWrestleClasses(page: HtmlPage): List<WrestleClass> {
    val wrestleClasses = mutableListOf<WrestleClass>()

    try {
        val tableBody = page.getFirstByXPath<HtmlTableBody>("//table[@id='ctl00_ContentPlaceHolderInhalt_dgrTurnierAltersklassen_ctl00']/tbody")
        if (tableBody != null) {
            val rows: List<HtmlTableRow> = tableBody.rows

            rows.forEach { row ->
                if (checkTableEmpty(row)) {
                    return wrestleClasses
                }
                val cells: List<HtmlTableCell> = row.cells

                val title = cells[0].asNormalizedText()
                val wrestleStyle = cells[1].asNormalizedText()
                val weightClasses = getWeightclasses(cells[2].asNormalizedText())
                val genders = determineGenders(cells[3].asNormalizedText(), cells[4].asNormalizedText())
                val ages = cells[5].asNormalizedText()
                val bracketMode = cells[6].asNormalizedText()

                val wrestleClass = WrestleClass().apply {
                    this.title = title
                    this.ages = ages
                    this.wrestleStyle = wrestleStyle
                    this.bracketMode = bracketMode

                    this.weightClasses = weightClasses.toRealmList()
                    this.genders = genders.toRealmSet()
                }

                wrestleClasses.add(wrestleClass)
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
        exitProcess(-1)
    }

    return wrestleClasses
}

fun scrapeRankings(page: HtmlPage, string: String): List<Ranking> {
    val results = mutableListOf<Ranking>()

    try {
        // Alle Siegerliste Links bekommen
        val resultLinks = page.getByXPath<HtmlAnchor>(
            "//div[contains(@id, 'GewichtLinkBlock') or contains(@name, 'GewichtLinkBlock')]//a[b][1]"
        ).map { it.getAttribute("href") }

        resultLinks.forEach {
            val link = string.substringBeforeLast("/") + "/"
            val completeLink = "$link$it"
            val rankingPage: HtmlPage = webClient.getPage(completeLink)

            // Dritten Table der Seite mit allen Rankings finden
            val tableBody = rankingPage.getFirstByXPath<HtmlTableBody>("(//table/tbody)[3]")
            val rows: List<HtmlTableRow> = tableBody.rows

            var weightClass = ""
            var ageClass = ""

            rows.forEachIndexed { index, row ->
                val cells: List<HtmlTableCell> = row.cells

                if (cells[0].getAttribute("class").contains("StdTitel")) {
                    weightClass = cells[0].asNormalizedText()
                    ageClass = cells[1].asNormalizedText()
                } else if (cells[0].getAttribute("class").contains("SLTextBold")) {
                    val ranking = Ranking()
                    ranking.apply {
                        //this.rank = cells[0].asNormalizedText().replace(".", "").toInt()
                        this.weightClass = weightClass
                        this.ageClass = ageClass
                        this.rank = cells[0].asNormalizedText()
                        this.name = cells[1].asNormalizedText()
                        this.club = cells[2].asNormalizedText()
                    }
                    results.add(ranking)
                }
            }

        }
    } catch (e: Exception) {
        e.printStackTrace()
        debugExit()

    } catch (pageNotFound: FailingHttpStatusCodeException) {
        // Manchmal gibt es die Dateien nicht, obwohl die Links da sind
        // Man könnte die anderen Links einzeln durchgehen, aber gerade kein Bock
        pageNotFound.printStackTrace()
        debugExit()
    }
    return results
}

fun scrapeRankingsAlternative() {

}

fun scrapePastUpcoming(page: HtmlPage): List<String> {
    val list = mutableListOf<String>()

    try {
        val items: List<HtmlListItem> = page.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lalZeitBereich']/ul/li")

        items.forEach { li ->
            var item = ""
            when (li.asNormalizedText()) {
                "Turnierergebnisse" -> item = "E"
                "Turnierkalender (kommende Turniere)" -> item = "K"
            }
            list.add(item)
        }

    } catch (e: Exception) {
        e.printStackTrace()
        debugExit()
    }

    return list
}

fun scrapeCountries(page: HtmlPage): List<String> {
    val list = mutableListOf<String>()

    try {
        val items: List<HtmlListItem> = page.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lalLandAuswahl']/ul/li")

        items.forEach { li ->
            list.add(li.asNormalizedText())
        }

    } catch (e: Exception) {
        e.printStackTrace()
        debugExit()
    }

    return list
}

fun scrapeYears(page: HtmlPage): List<String> {
    val list = mutableListOf<String>()

    try {
        val items: List<HtmlListItem> = page.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lallinkSaison']/ul/li")

        items.drop(1).forEach { li ->
            list.add(li.asNormalizedText())
        }

    } catch (e: Exception) {
        e.printStackTrace()
        debugExit()
    }

    return list
}