import androidx.compose.runtime.Composable
import di.sharedModule
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinApplication
import commons.theme.TakedownTheme
import commons.navigation.NavigationGraph

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(sharedModule())
        }
    ) {
        PreComposeApp {
            val navigator = rememberNavigator()
            TakedownTheme {
                NavigationGraph(
                    navigator = navigator,
                )
            }
        }
    }
}
