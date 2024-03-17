import androidx.compose.runtime.Composable
import com.rickclephas.kmm.viewmodel.KMMViewModel
import data.model.tournament.TurnierLatLng
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T : KMMViewModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    noinline definition: Definition<T>,
): KoinDefinition<T> = factory(qualifier = qualifier, definition = definition)

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