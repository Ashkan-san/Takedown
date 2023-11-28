import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import ui.RulesScreen
import ui.navigation.BottomNavItem
import ui.navigation.Screen
import ui.theme.AppTheme
import ui.turnier.TurnierDetailsScreen
import ui.turnier.TurniereListScreen
import viewmodel.TurnierViewModel

@Composable
fun App(viewModel: TurnierViewModel) {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme(useDarkTheme = false) {
            Navigation(navigator = navigator, viewModel = viewModel)
        }
    }
}

@Composable
fun Navigation(navigator: Navigator, viewModel: TurnierViewModel) {
    NavHost(
        navigator = navigator,
        initialRoute = BottomNavItem.Home.route
    ) {
        // HOME/TURNIERE
        scene(route = BottomNavItem.Home.route) {
            TurniereListScreen(navigator = navigator, viewModel = viewModel)
        }

        scene(route = Screen.TurnierDetails.route) {
            TurnierDetailsScreen(navigator = navigator, viewModel = viewModel)
        }


        // RULES
        scene(route = BottomNavItem.Rules.route) {
            RulesScreen(navigator = navigator)
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
