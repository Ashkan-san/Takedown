import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import data.Turnier
import data.TurnierAlterGewichtKlasse
import data.TurnierPlatzierung
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSpan
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow
import kotlin.system.measureTimeMillis

actual suspend fun fetchAllTurniere(): MutableList<Turnier> {
    val browser = setupWebClient()
    val turnierListe = mutableListOf<Turnier>()

    val executionTime = measureTimeMillis {
        val startJahr = "2023"
        val startLand = "DE"
        val startZeit = "E"

        val referenceLink = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${startJahr}&land=${startLand}&ZB=${startZeit}"
        val referencePage: HtmlPage = withContext(Dispatchers.IO) {
            browser.getPage(referenceLink)
        }

        // TODO später ändern
        val pastUpList = mutableListOf("E")
        val yearList = mutableListOf("2023")
        val countryList = mutableListOf("DE")

        /*val pastUpList = fetchPastUpcoming(referencePage)
        val countryList = fetchCountries(referencePage)
        val yearList = fetchYears(referencePage)*/

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
        val turnierListResults = turnierListDeferred.awaitAll()
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

    browser.close()

    return turnierAGList
}

actual suspend fun fetchDetails(turnier: Turnier): Turnier {
    val browser = setupWebClient()
    val detailsLink =
        "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=${turnier.id}"
    val detailsPage: HtmlPage = withContext(Dispatchers.IO) {
        browser.getPage(detailsLink)
    }

    val adresseSpan = detailsPage.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtWettkampfstaette']")
    val adresse = adresseSpan.textContent
    val landSpan = detailsPage.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtCountry']")
    val land = landSpan.textContent
    val ergebnisseLink = detailsPage.getFirstByXPath<HtmlAnchor>("//a[@id='ctl00_ContentPlaceHolderInhalt_lnkZuDenErgebnisse']").getAttribute("href")

    // TODO entweder hier callen oder beim clicken des "Ergebnisse" Tabs
    var ergebnisse = mutableStateListOf<TurnierPlatzierung>()
    if (ergebnisseLink.isNotEmpty()) {
        ergebnisse = fetchErgebnisse(ergebnisseLink).toMutableStateList()
    }
    browser.close()

    return turnier.copy(adresse = adresse, land = land, platzierungen = ergebnisse)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun Maps(address: String) {
    val locationState = remember { mutableStateOf<LatLng?>(null) }
    val context = LocalContext.current

    getLatLngFromAddress(context, address) { latlong ->
        locationState.value = latlong
    }

    if (locationState.value != null) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(locationState.value!!, 10f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = locationState.value!!),
                title = address,
                //snippet = "Marker in Singapore"
            )
        }
    }

}