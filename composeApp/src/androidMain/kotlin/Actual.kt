import android.media.AudioAttributes
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.rickclephas.kmm.viewmodel.KMMViewModel
import de.takedown.app.R
import data.model.tournament.TurnierLatLng
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import takedown.composeapp.generated.resources.Res
import takedown.composeapp.generated.resources.whistle_file_path
import java.io.File
import java.io.FileOutputStream


actual inline fun <reified T : KMMViewModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    noinline definition: Definition<T>,
): KoinDefinition<T> = viewModel(qualifier = qualifier, definition = definition)

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
    val filePath = stringResource(Res.string.whistle_file_path)
    var bytes by remember {
        mutableStateOf(ByteArray(0))
    }
    LaunchedEffect(Unit) {
        bytes = Res.readBytes(filePath)
        val tempFile = File.createTempFile("temp_audio", ".mp3")
        tempFile.deleteOnExit()

        val fos = FileOutputStream(tempFile)
        fos.write(bytes)
        fos.close()

        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(tempFile.absolutePath)
            prepare()
            start()
        }
    }
}

