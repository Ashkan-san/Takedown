import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import theme.AppTheme
import ui.navigation.NavigationGraph
import viewmodel.TurnierViewModel

@Composable
fun App(viewModel: TurnierViewModel) {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme() {
            NavigationGraph(navigator = navigator, viewModel = viewModel)
        }
    }
}
