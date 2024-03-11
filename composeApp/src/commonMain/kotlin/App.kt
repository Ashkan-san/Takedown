import androidx.compose.runtime.Composable
import di.sharedModule
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinApplication
import ui.theme.AppTheme
import ui.navigation.NavigationGraph
import ui.scoreboard.ScoreboardViewModel
import ui.tournaments.TournamentViewModel

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(sharedModule())
        }
    ) {
        PreComposeApp {
            val navigator = rememberNavigator()

            AppTheme {
                NavigationGraph(
                    navigator = navigator,
                )
            }
        }
    }
}
