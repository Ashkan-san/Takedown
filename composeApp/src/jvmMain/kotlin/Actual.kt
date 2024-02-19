import androidx.compose.runtime.Composable
import model.tournament.TurnierLatLng

@Composable
actual fun Maps(
    venue: String,
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