package commons.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject
import ui.fundamentals.FundamentalDetailsScreen
import ui.fundamentals.FundamentalsScreen
import ui.scoreboard.ScoreboardScreen
import ui.scoreboard.ScoreboardViewModel
import ui.tournaments.TournamentViewModel
import ui.tournaments.details.DetailsScreen
import ui.tournaments.details.results.RankingScreen
import ui.tournaments.overview.TournamentsScreen

@Composable
fun NavigationGraph(
    navigator: Navigator = rememberNavigator()
) {
    val tournamentViewModel = koinInject<TournamentViewModel>()
    val scoreboardViewModel = koinInject<ScoreboardViewModel>()

    NavHost(
        navigator = navigator,
        initialRoute = NavItem.Tournaments.route,
    ) {
        // TOURNAMENTS
        scene(
            route = NavItem.Tournaments.route
        ) {
            TournamentsScreen(
                onClickCard = { route -> navigator.navigate(route) },
                navigator = navigator,
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
        // FUNDAMENTALS
        scene(
            route = NavItem.Fundamentals.route,
            //deepLinks = listOf("{fundamentalDetails}")
        ) {
            FundamentalsScreen(
                onBack = { navigator.popBackStack() },
                //onClickCard = { route -> navigator.navigate("${NavItem.FundamentalDetails.route}/$route") }
                onClickCard = { navigator.navigate(NavItem.FundamentalDetails.route) }
            )
        }
        scene(
            route = NavItem.FundamentalDetails.route
        ) {
            FundamentalDetailsScreen()
        }
        //
        scene(route = NavItem.Moves.route) {
        }
        // SCOREBOARD
        scene(route = NavItem.Scoreboard.route) {
            ScoreboardScreen(
                viewModel = scoreboardViewModel,
                onBack = { navigator.popBackStack() }
            )
        }
        // ACCOUNT
        scene(route = NavItem.Account.route) {
        }
    }
}
