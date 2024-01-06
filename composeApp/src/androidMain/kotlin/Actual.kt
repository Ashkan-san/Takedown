import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import de.takedown.app.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import model.RingenKlasse
import model.Turnier
import model.TurnierLatLng
import model.TurnierPlatzierung
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSpan
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow
import scraper.determineGender
import scraper.determineWeightclass
import scraper.fetchAllTables
import scraper.fetchErgebnisse
import scraper.setupWebClient
import kotlin.system.measureTimeMillis

//val webClient = setupWebClient()

actual suspend fun fetchAllTurniere(): MutableList<Turnier> {
    val webClient = setupWebClient()
    val turnierListe = mutableListOf<Turnier>()

    val executionTime = measureTimeMillis {
        val startJahr = "2023"
        val startLand = "DE"
        val startZeit = "E"

        val referenceLink = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${startJahr}&land=${startLand}&ZB=${startZeit}"
        val referencePage: HtmlPage = withContext(Dispatchers.IO) {
            webClient.getPage(referenceLink)
        }

        // TODO später ändern
        val pastUpList = mutableListOf("E")
        val yearList = mutableListOf("2023")
        val countryList = mutableListOf("DE")

        /*val pastUpList = fetchPastUpcoming(referencePage)
        val countryList = fetchCountries(referencePage)
        val yearList = fetchYears(referencePage)*/

        val turnierListDeferred = mutableListOf<Deferred<MutableList<Turnier>>>()

        pastUpList.forEach { pastUp ->
            countryList.forEach { country ->
                yearList.forEach { year ->
                    coroutineScope {
                        val deferred = async {
                            val link = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${year}&land=${country}&ZB=${pastUp}"
                            val newHtml: HtmlPage = webClient.getPage(link)
                            fetchAllTables(newHtml)
                        }
                        turnierListDeferred.add(deferred)
                    }
                }
            }
        }
        val turnierListResults = turnierListDeferred.awaitAll()
        turnierListe.addAll(turnierListResults.flatten())

        webClient.close()
    }

    println("Execution time: $executionTime ms")
    return turnierListe
}

actual suspend fun fetchRingenKlassen(turnier: Turnier): MutableList<RingenKlasse> {
    val turnierAGList = mutableListOf<RingenKlasse>()

    try {
        val webClient = setupWebClient()
        val detailsLink =
            "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=${turnier.id}"
        val detailsPage: HtmlPage = withContext(Dispatchers.IO) {
            webClient.getPage(detailsLink)
        }

        val tableBody = detailsPage.getFirstByXPath<HtmlTableBody>("//table/tbody")
        val rows: List<HtmlTableRow> = tableBody.rows

        rows.forEach { row ->
            val cells: List<HtmlTableCell> = row.cells

            val altersKlasse = cells[0].asNormalizedText()
            val stilart = cells[1].asNormalizedText()
            val gewichtsKlassen = determineWeightclass(cells[2].asNormalizedText())
            val geschlecht = determineGender(cells[3].asNormalizedText(), cells[4].asNormalizedText())
            val jahrgaenge = cells[5].asNormalizedText()
            val modus = cells[6].asNormalizedText()

            val ringenKlasse = RingenKlasse(
                altersKlasse = altersKlasse,
                stilart = stilart,
                gewichtsKlassen = gewichtsKlassen,
                geschlecht = geschlecht,
                jahrgaenge = jahrgaenge,
                modus = modus
            )

            turnierAGList.add((ringenKlasse))
        }
        webClient.close()

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchAlterGewichtKlassen Exception: $e")
    }
    return turnierAGList
}

actual suspend fun fetchDetails(turnier: Turnier): Turnier {
    try {
        val webClient = setupWebClient()

        val detailsLink =
            "https://www.ringerdb.de/de/turniere/TurnierDetailInfo.aspx?TID=${turnier.id}"
        val detailsPage: HtmlPage = withContext(Dispatchers.IO) {
            webClient.getPage(detailsLink)
        }

        val adresseSpan = detailsPage.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtWettkampfstaette']")
        val adresse = adresseSpan.textContent
        val landSpan = detailsPage.getFirstByXPath<HtmlSpan>("//span[@id='ctl00_ContentPlaceHolderInhalt_txtCountry']")
        val land = landSpan.textContent
        val ergebnisseLink =
            detailsPage.getFirstByXPath<HtmlAnchor>("//a[@id='ctl00_ContentPlaceHolderInhalt_lnkZuDenErgebnisse']").getAttribute("href")

        var ergebnisse = mutableStateListOf<TurnierPlatzierung>()
        if (ergebnisseLink.isNotEmpty()) {
            ergebnisse = fetchErgebnisse(ergebnisseLink).toMutableStateList()
        }

        webClient.close()

        return turnier.copy(adresse = adresse, land = land, platzierungen = ergebnisse)
    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "FetchDetails Exception: $e")
    }

    return turnier
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun Maps(
    turnier: Turnier,
    location: TurnierLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
) {
    // TODO loading screen fixen
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()

    val mapTheme = if (isSystemInDarkTheme()) R.raw.map_dark else R.raw.map_light

    val mapProperties = remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapTheme)
            )
        )
    }

    // Location updaten, wenn neues Turnier
    LaunchedEffect(turnier) {
        getLatLngFromAddress(context, turnier.adresse) { latLng ->
            onUpdateLocation(latLng.latitude, latLng.longitude)
            onMapLoaded()
        }
    }

    location.let { loc ->
        DisposableEffect(loc) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(loc.lat, loc.lng),
                10f
            )

            onDispose {
                /* cleanup if needed */
            }
        }

        Box {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    onMapLoaded()
                },
                properties = mapProperties.value
            ) {
                Marker(
                    state = MarkerState(position = LatLng(location.lat, location.lng)),
                    title = turnier.adresse,
                )
            }
            if (!isMapLoaded) {
                AnimatedVisibility(
                    modifier = Modifier
                        .matchParentSize(),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}

