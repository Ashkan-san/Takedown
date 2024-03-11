package ui.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import ui.regelwerk.RulesScreen
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
        // TOURNAMENTS
        scene(
            route = NavItem.Tournaments.route
        ) {
            TournamentsScreen(
                navigator = navigator,
                viewModel = tournamentViewModel
            )
        }

        scene(
            route = NavItem.TournamentDetails.route,
            /*navTransition = NavTransition(
                createTransition = slideInHorizontally(animationSpec = tween(300)),
                //destroyTransition = ExitTransition()
            )*/
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

        // RULES
        scene(route = NavItem.Rules.route) {
            RulesScreen()
        }
        // MOVES
        scene(route = NavItem.Moves.route) {
        }
        // SCOREBOARD
        scene(route = NavItem.Scoreboard.route) {
            ScoreboardScreen(
                navigator = navigator,
                viewModel = scoreboardViewModel
            )
        }
        // ACCOUNT
        scene(route = NavItem.Account.route) {
        }
    }
}
