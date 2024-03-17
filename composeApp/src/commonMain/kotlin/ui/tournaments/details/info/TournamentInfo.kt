package ui.tournaments.details.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.model.tournament.Tournament
import data.model.tournament.TurnierLatLng

@Composable
fun TournamentInfo(
    tournament: Tournament,
    location: TurnierLatLng?,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        // TURNIER INFOS
        InfoBox(tournament)
        // ALTERS-/GEWICHTSKLASSEN
        WrestleClassesBox(tournament)
        // MAPS
        /*Maps(
            turnier = aktuellesTurnier,
            location = location!!,
            onUpdateLocation = { lat, lng -> onUpdateLocation(lat, lng) },
            isMapLoaded = isMapLoaded,
            onMapLoaded = { onMapLoaded() }
        )*/
    }
}