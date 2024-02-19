import androidx.compose.runtime.Composable
import model.tournament.TurnierLatLng

@Composable
expect fun Maps(
    venue: String,
    location: TurnierLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
)

@Composable
expect fun PlayWhistle()