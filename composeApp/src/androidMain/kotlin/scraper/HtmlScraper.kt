package scraper

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.turnier.Turnier
import model.turnier.TurnierPlatzierung
import org.htmlunit.TextPage
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlDivision
import org.htmlunit.html.HtmlListItem
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow

// TURNIERE VON JEDER SEITE
fun fetchAllTables(website: HtmlPage): MutableList<Turnier> {
    // Erster Fetch
    val turnierListe = fetchTable(website)

    if (turnierListe.isNotEmpty()) {
        var nextPageInput = website.getFirstByXPath<HtmlSubmitInput>("//input[@class='rgPageNext']")

        if (nextPageInput != null) {
            val pagesDiv = website.getFirstByXPath<HtmlDivision>("//div[@class='rgWrap rgNumPart']")
            val pageCount = pagesDiv.childElementCount

            // Solange es noch weitere Seiten gibt, weitere Fetches
            repeat(pageCount) {
                val nextPage = nextPageInput.click<HtmlPage>()
                // Seite lesen und in Liste packen
                val table = fetchTable(nextPage)
                turnierListe.addAll(table)
                // Page updaten
                nextPageInput = nextPage.getFirstByXPath("//input[@class='rgPageNext']")
            }
        }
    }

    return turnierListe
}

private fun fetchTable(page: HtmlPage): MutableList<Turnier> {
    val temporaryList = mutableListOf<Turnier>()
    // Table finden und Rows in Liste, dann über Rows iterieren
    val tableBody = page.getFirstByXPath<HtmlTableBody>("//table[@class='rgMasterTable']/tbody")
    val rows: List<HtmlTableRow> = tableBody.rows

    rows.forEach { row ->
        val cells: List<HtmlTableCell> = row.cells

        val datum = cells[0].asNormalizedText()
        val titel = cells[1].asNormalizedText()
        val ort = cells[2].asNormalizedText()
        val veranstalter = cells[3].asNormalizedText()
        val verein = cells[4].asNormalizedText()
        val detailsLink = cells[5].getFirstByXPath<HtmlAnchor>(".//a").getAttribute("href")

        val id = detailsLink.replace("TurnierDetailInfo.aspx?TID=", "")
        val turnierDatum = parseDate(datum)

        val turnier = Turnier(
            id = id,
            datum = turnierDatum,
            beendet = isTurnierBeendet(turnierDatum.datumString),
            titel = titel,
            stadt = ort,
            veranstalter = veranstalter,
            verein = verein,
            alterGewichtsKlassen = mutableStateListOf(),
            platzierungen = mutableStateListOf()
        )

        temporaryList.add((turnier))
    }

    return temporaryList
}

// JAHRE, LÄNDER, ERGEBNISSE/KOMMENDE
fun fetchPastUpcoming(website: HtmlPage): MutableList<String> {
    val pastUpList = mutableListOf<String>()

    try {
        val pastUpLi: List<HtmlListItem> =
            website.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lalZeitBereich']/ul/li")

        pastUpLi.forEach { li ->
            var item = ""
            when (li.asNormalizedText()) {
                "Turnierergebnisse" -> item = "E"
                "Turnierkalender (kommende Turniere)" -> item = "K"
            }
            pastUpList.add(item)
        }

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchPastUpcoming Exception: $e")
    }

    return pastUpList
}

fun fetchCountries(website: HtmlPage): MutableList<String> {
    val countryList = mutableListOf<String>()

    try {
        val countryLi: List<HtmlListItem> =
            website.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lalLandAuswahl']/ul/li")

        countryLi.forEach { li ->
            countryList.add(li.asNormalizedText())
        }

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchCountries Exception: $e")
    }

    return countryList
}

fun fetchYears(website: HtmlPage): MutableList<String> {
    // die jahre auf seite durchgehen und in liste packen
    val yearList = mutableListOf<String>()

    try {
        val yearLi: List<HtmlListItem> =
            website.getByXPath("//div[@id='ctl00_ContentPlaceHolderInhalt_lallinkSaison']/ul/li")

        // Für alle Jahre die Zahlen kriegen
        yearLi.drop(1).forEach { li ->
            yearList.add(li.asNormalizedText())
        }

        // Kommendes Jahr auch noch hinzufügen, weil bei DE/23/E nur bis aktuellem Jahr ist
        val nextYear = yearList.last().toInt().plus(1).toString()
        yearList.add(nextYear)

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchYears Exception: $e")
    }

    return yearList
}

// ERGEBNISSE
suspend fun fetchErgebnisse(ergebnisseLink: String): MutableList<TurnierPlatzierung> {
    val webClient = setupWebClient()
    val ergebnissePage: HtmlPage = withContext(Dispatchers.IO) {
        webClient.getPage(ergebnisseLink)
    }

    // TODO noch andere Ergebnisse aus Ländern anschauen, weil unterschiedlich
    val platzierungenLink = ergebnissePage.getFirstByXPath<HtmlAnchor>(
        "//a[contains(text(), 'Siegerliste Tab separiert') or contains(text(), 'List of winners as text file (tab separated)')]"
    )

    if (platzierungenLink == null) {
        webClient.close()
        return mutableListOf()
    }

    val linkString = platzierungenLink.hrefAttribute
    val platzierungenPage: TextPage = withContext(Dispatchers.IO) {
        val ganzerLink = ergebnisseLink.replace(Regex("/[^/]*$"), "/").plus(linkString)
        webClient.getPage(ganzerLink)
    }

    val platzierungenFileString = platzierungenPage.content
    webClient.close()

    return parseSiegerList(platzierungenFileString)
}
