import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import ui.theme.AppTheme
import viewmodel.TurnierViewModel

@Composable
fun App(viewModel: TurnierViewModel) {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme(useDarkTheme = false) {
            NavigationGraph(navigator = navigator, viewModel = viewModel)
        }
    }
}
