package ui.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import ui.regelwerk.RulesScreen
import ui.turnier.details.TurnierDetailsScreen
import ui.turnier.liste.TurniereScreen
import ui.turnier.ranking.TurnierRankingScreen
import viewmodel.TurnierViewModel

@Composable
fun NavigationGraph(navigator: Navigator, viewModel: TurnierViewModel) {
    NavHost(
        navigator = navigator,
        initialRoute = BottomNavItem.Turniere.route
    ) {
        // HOME/TURNIERE
        scene(
            route = BottomNavItem.Turniere.route
        ) {
            TurniereScreen(navigator = navigator, viewModel = viewModel)
        }

        scene(
            route = Screen.TurnierDetails.route,
            /*navTransition = NavTransition(
                createTransition = slideInHorizontally(animationSpec = tween(300)),
                //destroyTransition = ExitTransition()
            )*/
        ) {
            TurnierDetailsScreen(navigator = navigator, viewModel = viewModel)
        }

        scene(route = Screen.TurnierRanking.route) {
            TurnierRankingScreen(navigator = navigator, viewModel = viewModel)
        }


        // RULES
        scene(route = BottomNavItem.Rules.route) {
            RulesScreen(navigator = navigator, viewModel = viewModel)
        }
        // MOVES
        scene(route = BottomNavItem.Moves.route) {

        }
        // SCOREBOARD
        scene(route = BottomNavItem.Scoreboard.route) {

        }
        // ACCOUNT
        scene(route = BottomNavItem.Account.route) {

        }
    }
}