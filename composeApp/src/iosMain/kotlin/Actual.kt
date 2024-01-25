import androidx.compose.runtime.Composable
import model.turnier.RingenKlasse
import model.turnier.Turnier
import model.turnier.TurnierLatLng

actual suspend fun fetchAllTurniere(): MutableList<Turnier> {
    TODO("Not yet implemented")
}

actual suspend fun fetchRingenKlassen(turnier: Turnier): MutableList<RingenKlasse> {
    TODO("Not yet implemented")
}

actual suspend fun fetchDetails(turnier: Turnier): Turnier {
    TODO("Not yet implemented")
}

@Composable
actual fun Maps(
    turnier: Turnier,
    location: TurnierLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit,
) {
    TODO("Not yet implemented")
}

@Composable
actual fun PlayWhistle() {
    TODO("Not yet implemented")
}