package ui.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import ui.regelwerk.RulesScreen
import ui.scoreboard.ScoreboardScreen
import ui.scoreboard.ScoreboardViewModel
import ui.turnier.TurnierViewModel
import ui.turnier.details.TurnierDetailsScreen
import ui.turnier.liste.TurniereScreen
import ui.turnier.ranking.TurnierRankingScreen

@Composable
fun NavigationGraph(
    navigator: Navigator,
    turnierViewModel: TurnierViewModel,
    scoreboardViewModel: ScoreboardViewModel
) {
    NavHost(
        navigator = navigator,
        initialRoute = BottomNavItem.Turniere.route
    ) {
        // HOME/TURNIERE
        scene(
            route = BottomNavItem.Turniere.route
        ) {
            TurniereScreen(navigator = navigator, viewModel = turnierViewModel)
        }

        scene(
            route = Screen.TurnierDetails.route,
            /*navTransition = NavTransition(
                createTransition = slideInHorizontally(animationSpec = tween(300)),
                //destroyTransition = ExitTransition()
            )*/
        ) {
            TurnierDetailsScreen(navigator = navigator, viewModel = turnierViewModel)
        }

        scene(route = Screen.TurnierRanking.route) {
            TurnierRankingScreen(navigator = navigator, viewModel = turnierViewModel)
        }


        // RULES
        scene(route = BottomNavItem.Rules.route) {
            RulesScreen(navigator = navigator, viewModel = turnierViewModel)
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