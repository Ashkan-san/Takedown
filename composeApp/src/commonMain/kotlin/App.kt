import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import theme.AppTheme
import ui.navigation.NavigationGraph
import ui.scoreboard.ScoreboardViewModel
import ui.turnier.TurnierViewModel

@Composable
fun App(
    turnierViewModel: TurnierViewModel,
    scoreboardViewModel: ScoreboardViewModel
) {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme() {
            NavigationGraph(navigator = navigator, turnierViewModel = turnierViewModel, scoreboardViewModel = scoreboardViewModel)
        }
    }
}
