package viewmodel

import data.Turnier
import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import data.TurnierDatum
import data.TurnierDetails
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.htmlunit.BrowserVersion
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlDivision
import org.htmlunit.html.HtmlListItem
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow
import kotlin.system.measureTimeMillis

private fun setupWebClient(): WebClient {
    val webClient = WebClient(BrowserVersion.CHROME)
    webClient.options.isThrowExceptionOnScriptError = false
    webClient.options.isCssEnabled = false
    webClient.javaScriptTimeout = 120000

    return webClient
}

actual suspend fun fetchAllTurniere(): MutableList<Turnier> {
    val browser = setupWebClient()
    val turnierListe = mutableListOf<Turnier>()

    val executionTime = measureTimeMillis {
        val startJahr = "2023"
        val startLand = "DE"
        val startZeit = "E"

        val referenceLink =
            "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${startJahr}&land=${startLand}&ZB=${startZeit}"
        val referencePage: HtmlPage = withContext(Dispatchers.IO) {
            browser.getPage(referenceLink)
        }

        // TODO später ändern
        //val pastUpList = fetchPastUpcoming(referenceHtml)
        val pastUpList = mutableListOf("K")
        //val countryList = fetchCountries(referenceHtml)
        val yearList = mutableListOf("2023", "2024")
        val countryList = mutableListOf("DE")
        //val yearList = fetchYears(referenceHtml)
        val turnierListDeferred = mutableListOf<Deferred<MutableList<Turnier>>>()

        println("$pastUpList, $countryList, $yearList")
        pastUpList.forEach { pastUp ->
            countryList.forEach { country ->
                yearList.forEach { year ->

                    coroutineScope {
                        val deferred = async {
                            val link = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${year}&land=${country}&ZB=${pastUp}"
                            val newHtml: HtmlPage = browser.getPage(link)
                            fetchAllTables(newHtml)
                        }
                        turnierListDeferred.add(deferred)
                    }

                }
            }
        }
        // Wait for all deferred tasks to complete
        val turnierListResults = turnierListDeferred.awaitAll()
        // Combine results
        turnierListe.addAll(turnierListResults.flatten())

        browser.close()
    }

    println("Execution time: $executionTime ms")
    // Duplikate entfernen
    return turnierListe.distinct().toMutableList()
}

private fun fetchPastUpcoming(website: HtmlPage): MutableList<String> {
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

private fun fetchCountries(website: HtmlPage): MutableList<String> {
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

        // Kommendes Jahr auch noch hinzufügen, weil bei DE/23/E nur bis aktuellem Jahr ist
        val nextYear = yearList.last().toInt().plus(1).toString()
        yearList.add(nextYear)

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

        if (turnierListe.isNotEmpty()) {
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
        val detailsLink = cells[5].getFirstByXPath<HtmlAnchor>(".//a").getAttribute("href")

        //println("Anchor: " + details + " Text: " + details.asNormalizedText() + " Attribute: " + details.getAttribute("href"))

        val turnierDatum = parseDate(datum)

        val turnier = Turnier(
            datum = turnierDatum,
            titel = titel,
            ort = ort,
            veranstalter = veranstalter,
            verein = verein,
            detailsLink = detailsLink,
            turnierDetails = mutableStateListOf()
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
    val matchResult = regex.find(dateString) ?: return TurnierDatum("", "", "", "")
    val (startTag, endTag, monat, jahr) = matchResult.destructured

    return TurnierDatum(startTag, endTag, monat, jahr)
}

actual suspend fun fetchTurnierDetails(turnier: Turnier): MutableList<TurnierDetails> {
    val browser = setupWebClient()
    val detailsLink =
        "https://www.ringerdb.de/de/Turniere/${turnier.detailsLink}"
    val detailsPage: HtmlPage = withContext(Dispatchers.IO) {
        browser.getPage(detailsLink)
    }

    val tableBody = detailsPage.getFirstByXPath<HtmlTableBody>("//table/tbody")
    val rows: List<HtmlTableRow> = tableBody.rows

    val turnierDetailsList = mutableListOf<TurnierDetails>()

    rows.forEach { row ->
        val cells: List<HtmlTableCell> = row.cells

        val altersKlasse = cells[0].asNormalizedText()
        val stilart = cells[1].asNormalizedText()
        val gewichtsKlassen = determineWeightclass(cells[2].asNormalizedText())
        val geschlecht = determineGender(cells[3].asNormalizedText(), cells[4].asNormalizedText())
        val jahrgaenge = cells[5].asNormalizedText()
        val modus = cells[6].asNormalizedText()

        val turnierDetails = TurnierDetails(
            altersKlasse = altersKlasse,
            stilart = stilart,
            gewichtsKlassen = gewichtsKlassen,
            geschlecht = geschlecht,
            jahrgaenge = jahrgaenge,
            modus = modus
        )

        turnierDetailsList.add((turnierDetails))
        //println("Datum: $datum Titel: $titel Ort: $ort Veranstalter: $veranstalter Verein: $verein Details: $details")
    }

    return turnierDetailsList
}

fun determineGender(male: String, female: String): MutableList<String> {
    val genderList = mutableListOf<String>()

    if (male == "X") {
        genderList.add("Männlich")
    } else if (female == "X") {
        genderList.add("Weiblich")
    } else {
        return genderList
    }

    return genderList
}

fun determineWeightclass(weightClasses: String): MutableList<String>  {
    val weightClassList = mutableListOf<String>()
    if (weightClasses == "Freie Einteilung") {
        weightClassList.add(weightClasses)
    } else {
        val numberStrings = weightClasses.split(", ")
        weightClassList.addAll(numberStrings)
    }

    return weightClassList
}
