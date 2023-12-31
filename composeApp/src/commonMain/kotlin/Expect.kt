import androidx.compose.runtime.Composable
import data.MyLatLng
import data.RingenKlasse
import data.Turnier

expect suspend fun fetchAllTurniere(): MutableList<Turnier>

expect suspend fun fetchRingenKlassen(turnier: Turnier): MutableList<RingenKlasse>

expect suspend fun fetchDetails(turnier: Turnier): Turnier

@Composable
expect fun Maps(
    turnier: Turnier,
    location: MyLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
)