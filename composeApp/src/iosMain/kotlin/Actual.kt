import androidx.compose.runtime.Composable
import data.MyLatLng
import data.RingenKlasse
import data.Turnier

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
    location: MyLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit,
) {
    TODO("Not yet implemented")
}