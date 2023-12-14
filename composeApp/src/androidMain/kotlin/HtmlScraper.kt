import data.Turnier
import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import data.TurnierDatum
import data.TurnierPlatzierung
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.htmlunit.BrowserVersion
import org.htmlunit.TextPage
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlDivision
import org.htmlunit.html.HtmlListItem
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow

fun setupWebClient(): WebClient {
    val webClient = WebClient(BrowserVersion.CHROME)
    webClient.options.isThrowExceptionOnScriptError = false
    webClient.options.isCssEnabled = false
    webClient.javaScriptTimeout = 120000

    return webClient
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

        println(cells)
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

// ERGEBNISSE
suspend fun fetchErgebnisse(ergebnisseLink: String): MutableList<TurnierPlatzierung> {
    val browser = setupWebClient()

    val ergebnissePage: HtmlPage = withContext(Dispatchers.IO) {
        browser.getPage(ergebnisseLink)
    }

    val platzierungenLink = ergebnissePage.getFirstByXPath<HtmlAnchor>("//a[contains(text(), 'Siegerliste Tab separiert')]").hrefAttribute

    val platzierungenPage: TextPage = withContext(Dispatchers.IO) {
        val ganzerLink = ergebnisseLink.replace(Regex("/[^/]*$"), "/").plus(platzierungenLink)
        browser.getPage(ganzerLink)
    }

    val platzierungenFileString = platzierungenPage.content
    browser.close()

    return readTextFromString(platzierungenFileString)
}

fun readTextFromString(input: String): MutableList<TurnierPlatzierung> {
    val resultList = mutableListOf<TurnierPlatzierung>()
    val lines = input.dropLast(2).split("\n")
    val skipFirst = 4
    val linesToProcess = lines.drop(skipFirst)

    for (line in linesToProcess) {
        if (line.isNotBlank()) {
            val parts = line.split("\t").filter { it.isNotBlank() && !it.contains(Regex("\\(.*\\)")) }

            if (parts.size == 2) {
                val weight = parts[0].replace("kg", "")
                val age = parts[1]
                resultList.add(TurnierPlatzierung(weight, age))
            }
            if (parts.size == 3) {
                val rank = parts[0].replace(".", "")
                val name = parts[1]
                val club = parts[2]

                resultList.last().apply {
                    platzierung = rank
                    ringerName = name
                    verein = club
                }
            }
        }
    }

    return resultList
}

// HELPER METHODEN
fun parseDate(dateString: String): TurnierDatum {
    // 2 Zahlen
    // Optionale Minus mit 2 Zahlen
    // 2 Zahlen
    // 4 Zahlen
    // Beispiel: 04.-05.11.2023
    val regex = """(\d{2})(?:\.-(\d{2}))?\.(\d{2})\.(\d{4})""".toRegex()
    val matchResult = regex.find(dateString) ?: return TurnierDatum("", "", "", "", dateString)
    val (startTag, endTag, monat, jahr) = matchResult.destructured

    return TurnierDatum(startTag, endTag, monat, jahr, dateString)
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

fun determineWeightclass(weightClasses: String): MutableList<String> {
    val weightClassList = mutableListOf<String>()
    if (weightClasses == "Freie Einteilung") {
        weightClassList.add(weightClasses)
    } else {
        val numberStrings = weightClasses.split(", ")
        weightClassList.addAll(numberStrings)
    }

    return weightClassList
}
