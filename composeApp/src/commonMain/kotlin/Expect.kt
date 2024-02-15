import androidx.compose.runtime.Composable
import model.turnier.Turnier
import model.turnier.TurnierLatLng

@Composable
expect fun Maps(
    turnier: Turnier,
    location: TurnierLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
)

@Composable
expect fun PlayWhistle()