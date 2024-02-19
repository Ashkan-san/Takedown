import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import de.takedown.app.R
import model.tournament.TurnierLatLng


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun Maps(
    venue: String,
    location: TurnierLatLng,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
) {
    // TODO loading screen fixen
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()

    val mapTheme = if (isSystemInDarkTheme()) R.raw.map_dark else R.raw.map_light

    val mapProperties = remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapTheme)
            )
        )
    }

    // Location updaten, wenn neues Turnier
    LaunchedEffect(venue) {
        getLatLngFromAddress(context, venue) { latLng ->
            onUpdateLocation(latLng.latitude, latLng.longitude)
            onMapLoaded()
        }
    }

    location.let { loc ->
        DisposableEffect(loc) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(loc.lat, loc.lng),
                10f
            )

            onDispose {
                /* cleanup if needed */
            }
        }

        Box {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    onMapLoaded()
                },
                properties = mapProperties.value
            ) {
                Marker(
                    state = MarkerState(position = LatLng(location.lat, location.lng)),
                    title = venue,
                )
            }

            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
actual fun PlayWhistle() {
    val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.whistle)
    mediaPlayer.start()
}

