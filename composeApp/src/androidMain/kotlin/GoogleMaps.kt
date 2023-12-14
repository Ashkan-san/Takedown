import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getLatLngFromAddress(context: Context, address: String, callback: (LatLng) -> Unit) {
    val coder = Geocoder(context)

    val geocodeListener = Geocoder.GeocodeListener { addresses ->
        val location = addresses[0]
        val latlong = LatLng(location.latitude, location.longitude)
        callback(latlong)
    }

    coder.getFromLocationName(address, 5, geocodeListener)
}