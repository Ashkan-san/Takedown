package commons.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import ui.fundamentals.FundamentalsScreen
import ui.scoreboard.ScoreboardScreen
import ui.scoreboard.ScoreboardViewModel
import ui.tournaments.TournamentViewModel
import ui.tournaments.details.DetailsScreen
import ui.tournaments.details.results.RankingScreen
import ui.tournaments.list.TournamentsScreen

@Composable
fun NavigationGraph(
    navigator: Navigator
) {
    val tournamentViewModel = koinInject<TournamentViewModel>()
    val scoreboardViewModel = koinInject<ScoreboardViewModel>()

    NavHost(
        navigator = navigator,
        initialRoute = NavItem.Tournaments.route,
    ) {
        scene(
            route = NavItem.Tournaments.route
        ) {
            TournamentsScreen(
                currentRoute = navigator.currentEntry.collectAsState(null).value?.route?.route,
                onClickNavBarItem = { route -> navigator.navigate(route) },
                onClickCard = { route -> navigator.navigate(route) },
                viewModel = tournamentViewModel
            )
        }
        scene(
            route = NavItem.TournamentDetails.route,
        ) {
            DetailsScreen(
                navigator = navigator,
                viewModel = tournamentViewModel
            )
        }
        scene(route = NavItem.TournamentRanking.route) {
            RankingScreen(
                navigator = navigator,
                viewModel = tournamentViewModel
            )
        }
        // TODO richtig
        scene(
            route = NavItem.Fundamentals.route,
            deepLinks = listOf("{fundamentalDetails}")
        ) {
            FundamentalsScreen(
                onBack = { navigator.popBackStack() },
                onClickCard = { route -> navigator.navigate("${NavItem.FundamentalDetails.route}/$route") }
            )
        }
        scene(route = NavItem.Moves.route) {
        }
        scene(route = NavItem.Scoreboard.route) {
            ScoreboardScreen(
                viewModel = scoreboardViewModel,
                onBack = { navigator.popBackStack() }
            )
        }
        scene(route = NavItem.Account.route) {
        }
    }
}
