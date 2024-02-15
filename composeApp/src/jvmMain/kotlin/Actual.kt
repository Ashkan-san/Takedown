import androidx.compose.runtime.Composable
import model.turnier.Turnier
import model.turnier.TurnierLatLng

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