import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import theme.AppTheme
import ui.navigation.NavigationGraph
import ui.scoreboard.ScoreboardViewModel
import ui.tournaments.TournamentViewModel

@Composable
fun App(
    tournamentViewModel: TournamentViewModel,
    scoreboardViewModel: ScoreboardViewModel
) {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme() {
            NavigationGraph(navigator = navigator, tournamentViewModel = tournamentViewModel, scoreboardViewModel = scoreboardViewModel)
        }
    }
}
