import androidx.compose.runtime.Composable
import com.rickclephas.kmm.viewmodel.KMMViewModel
import model.tournament.TurnierLatLng
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect inline fun <reified T : KMMViewModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

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