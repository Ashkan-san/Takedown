package viewmodel

import data.Turnier
import android.content.ContentValues
import android.util.Log
import data.TurnierDatum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.htmlunit.BrowserVersion
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlDivision
import org.htmlunit.html.HtmlListItem
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow

actual suspend fun fetchAllTurniere(): MutableList<Turnier> {
    val browser = setupWebClient()
    val turnierListe = mutableListOf<Turnier>()

    val startJahr = "2001"
    val startLand = "DE"
    val startZeit = "K"

    val startLink =
        "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${startJahr}&land=${startLand}&ZB=${startZeit}"
    val startHtml: HtmlPage = withContext(Dispatchers.IO) {
        browser.getPage(startLink)
    }

    fetchYears(startHtml).forEach { year ->
        println(year)
        val link = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${year}&land=DE&ZB=K"
        val newHtml: HtmlPage = withContext(Dispatchers.IO) {
            browser.getPage(link)
        }
        turnierListe.addAll(fetchAllTables(newHtml))
    }

    // Duplikate entfernen
    browser.close()
    return turnierListe.distinct().toMutableList()
}

private fun setupWebClient(): WebClient {
    val webClient = WebClient(BrowserVersion.CHROME)
    webClient.options.isThrowExceptionOnScriptError = false
    webClient.options.isCssEnabled = false
    webClient.javaScriptTimeout = 30000

    return webClient
}

private fun fetchYears(website: HtmlPage): MutableList<String> {
    // die jahre auf seite durchgehen und in liste packen
    val yearList = mutableListOf<String>()

    try {
        val yearLi: List<HtmlListItem> =
            website.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lallinkSaison']/ul/li")

        // Für alle Jahre die Zahlen kriegen
        yearLi.drop(1).forEach { li ->
            yearList.add(li.asNormalizedText())
        }

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchYears Exception: $e")
    }

    return yearList
}

private fun fetchAllTables(website: HtmlPage): MutableList<Turnier> {
    var turnierListe = mutableListOf<Turnier>()
    try {
        // Erster Fetch
        turnierListe = fetchTable(website)

        var nextPageInput = website.getFirstByXPath<HtmlSubmitInput>("//input[@class='rgPageNext']")
        val pagesDiv = website.getFirstByXPath<HtmlDivision>("//div[@class='rgWrap rgNumPart']")
        val pageCount = pagesDiv.childElementCount

        // Solange es noch weitere Seiten gibt, weitere Fetches
        repeat(pageCount) {
            if (nextPageInput != null) {
                val nextPage = nextPageInput.click<HtmlPage>()

                //browser.waitForBackgroundJavaScript(1000)
                // Seite lesen und in Liste packen
                val table = fetchTable(nextPage)
                turnierListe.addAll(table)

                // Page updaten
                nextPageInput = nextPage.getFirstByXPath("//input[@class='rgPageNext']")
            }
        }

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchAllTables Exception: $e")
    }

    return turnierListe
}

private fun fetchTable(nextPage: HtmlPage): MutableList<Turnier> {
    val temporaryList = mutableListOf<Turnier>()
    // Table finden und Rows in Liste, dann über Rows iterieren
    val tableBody = nextPage.getFirstByXPath<HtmlTableBody>("//table/tbody")
    val rows: List<HtmlTableRow> = tableBody.rows

    rows.forEach { row ->
        val cells: List<HtmlTableCell> = row.cells

        val datum = cells[0].asNormalizedText()
        val titel = cells[1].asNormalizedText()
        val ort = cells[2].asNormalizedText()
        val veranstalter = cells[3].asNormalizedText()
        val verein = cells[4].asNormalizedText()
        val details = cells[5].asNormalizedText()

        val turnierDatum = parseDate(datum)

        val turnier = Turnier(
            datum = turnierDatum,
            titel = titel,
            ort = ort,
            veranstalter = veranstalter,
            verein = verein,
            details = details
        )

        temporaryList.add((turnier))
        //println("Datum: $datum Titel: $titel Ort: $ort Veranstalter: $veranstalter Verein: $verein Details: $details")
    }
    return temporaryList
}

fun parseDate(dateString: String): TurnierDatum {
    // 2 Zahlen
    // Optionale Minus mit 2 Zahlen
    // 2 Zahlen
    // 4 Zahlen
    // Beispiel: 04.-05.11.2023
    val regex = """(\d{2})(?:\.-(\d{2}))?\.(\d{2})\.(\d{4})""".toRegex()
    val matchResult = regex.find(dateString) ?: return TurnierDatum()
    val (startTag, endTag, monat, jahr) = matchResult.destructured

    return TurnierDatum(startTag, endTag, monat, jahr)
}

