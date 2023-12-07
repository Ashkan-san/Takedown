import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import data.Turnier
import data.TurnierAlterGewichtKlasse
import de.takedown.app.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSpan
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow
import viewmodel.determineGender
import viewmodel.determineWeightclass
import viewmodel.fetchAllTables
import viewmodel.setupWebClient
import kotlin.system.measureTimeMillis

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
    return turnierListe.distinctBy { it.id }.toMutableList()
}

actual suspend fun fetchAlterGewichtKlassen(turnier: Turnier): MutableList<TurnierAlterGewichtKlasse> {
    val browser = setupWebClient()
    val detailsLink =
        "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=${turnier.id}"
    val detailsPage: HtmlPage = withContext(Dispatchers.IO) {
        browser.getPage(detailsLink)
    }

    val tableBody = detailsPage.getFirstByXPath<HtmlTableBody>("//table/tbody")
    val rows: List<HtmlTableRow> = tableBody.rows
    val turnierAGList = mutableListOf<TurnierAlterGewichtKlasse>()

    rows.forEach { row ->
        val cells: List<HtmlTableCell> = row.cells

        val altersKlasse = cells[0].asNormalizedText()
        val stilart = cells[1].asNormalizedText()
        val gewichtsKlassen = determineWeightclass(cells[2].asNormalizedText())
        val geschlecht = determineGender(cells[3].asNormalizedText(), cells[4].asNormalizedText())
        val jahrgaenge = cells[5].asNormalizedText()
        val modus = cells[6].asNormalizedText()

        val turnierAlterGewichtKlasse = TurnierAlterGewichtKlasse(
            altersKlasse = altersKlasse,
            stilart = stilart,
            gewichtsKlassen = gewichtsKlassen,
            geschlecht = geschlecht,
            jahrgaenge = jahrgaenge,
            modus = modus
        )

        turnierAGList.add((turnierAlterGewichtKlasse))
    }

    return turnierAGList
}

actual suspend fun fetchDetails(turnier: Turnier): Turnier {
    val browser = setupWebClient()
    val detailsLink =
        "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=${turnier.id}"
    val detailsPage: HtmlPage = withContext(Dispatchers.IO) {
        browser.getPage(detailsLink)
    }

    val adressSpan = detailsPage.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtWettkampfstaette']")
    val adresse = adressSpan.textContent
    val landSpan = detailsPage.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtCountry']")
    val land = landSpan.textContent

    return turnier.copy(adresse = adresse, land = land)
}

@Composable
actual fun getTurnierBild(): Painter {
    return painterResource(R.drawable.jordan)
}

@Composable
actual fun getTakedownLogo(): Painter {
    return painterResource(R.drawable.ic_launcher_foreground)
}