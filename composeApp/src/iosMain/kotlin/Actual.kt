import androidx.compose.runtime.Composable
import model.RingenKlasse
import model.Turnier
import model.TurnierLatLng

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