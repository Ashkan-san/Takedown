package ui.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import ui.regelwerk.RulesScreen
import ui.scoreboard.ScoreboardScreen
import ui.scoreboard.ScoreboardViewModel
import ui.tournaments.TournamentViewModel
import ui.tournaments.details.DetailsScreen
import ui.tournaments.details.results.RankingScreen
import ui.tournaments.list.TournamentsScreen

@Composable
fun NavigationGraph(
    navigator: Navigator,
    tournamentViewModel: TournamentViewModel,
    scoreboardViewModel: ScoreboardViewModel
) {
    NavHost(
        navigator = navigator,
        initialRoute = BottomNavItem.Tournaments.route // TODO später ändern, nur debug
    ) {
        // HOME/TURNIERE
        scene(
            route = BottomNavItem.Tournaments.route
        ) {
            TournamentsScreen(navigator = navigator, viewModel = tournamentViewModel)
        }

        scene(
            route = Screen.TournamentDetails.route,
            /*navTransition = NavTransition(
                createTransition = slideInHorizontally(animationSpec = tween(300)),
                //destroyTransition = ExitTransition()
            )*/
        ) {
            DetailsScreen(navigator = navigator, viewModel = tournamentViewModel)
        }

        scene(route = Screen.TournamentRanking.route) {
            RankingScreen(navigator = navigator, viewModel = tournamentViewModel)
        }


        // RULES
        scene(route = BottomNavItem.Rules.route) {
            RulesScreen(navigator = navigator, viewModel = tournamentViewModel)
        }
        // MOVES
        scene(route = BottomNavItem.Moves.route) {

        }
        // SCOREBOARD
        scene(route = BottomNavItem.Scoreboard.route) {
            ScoreboardScreen(navigator = navigator, viewModel = scoreboardViewModel)
        }
        // ACCOUNT
        scene(route = BottomNavItem.Account.route) {

        }
    }
}