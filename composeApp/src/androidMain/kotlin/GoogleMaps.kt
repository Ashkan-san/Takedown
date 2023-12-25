import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getLatLngFromAddress(context: Context, address: String, callback: (LatLng) -> Unit) {
    val coder = Geocoder(context)

    val geocodeListener = Geocoder.GeocodeListener { addresses ->
        // TODO Karte besser hinkriegen, weil nur Stadtname reicht nicht anscheinend
        //println("ORT" + addresses)
        var latLng: LatLng? = null

        for (location in addresses) {
            if (location.hasLatitude() && location.hasLongitude()) {
                latLng = LatLng(location.latitude, location.longitude)
                break
            }
        }

        latLng?.let { callback(it) }
    }

    coder.getFromLocationName(address, 5, geocodeListener)
}