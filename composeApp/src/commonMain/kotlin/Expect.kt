import androidx.compose.runtime.Composable
import model.RingenKlasse
import model.Turnier
import model.TurnierLatLng

expect suspend fun fetchAllTurniere(): MutableList<Turnier>

expect suspend fun fetchRingenKlassen(turnier: Turnier): MutableList<RingenKlasse>

expect suspend fun fetchDetails(turnier: Turnier): Turnier

@Composable
expect fun Maps(
    turnier: Turnier,
    location: TurnierLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
)