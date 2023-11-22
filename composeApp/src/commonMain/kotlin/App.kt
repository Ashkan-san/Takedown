import androidx.compose.runtime.Composable
import data.Turnier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import ui.RulesScreen
import ui.navigation.BottomNavItem
import ui.theme.AppTheme
import ui.turnier.TurnierScreen

@Composable
fun App(turniere: MutableList<Turnier>) {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme(useDarkTheme = false) {
            Navigation(navigator = navigator, turniere = turniere)
        }
    }
}

@Composable
fun Navigation(navigator: Navigator, turniere: MutableList<Turnier>) {
    NavHost(
        navigator = navigator,
        initialRoute = BottomNavItem.Home.route
    ) {
        // HOME/TURNIERE
        scene(route = BottomNavItem.Home.route) {
            TurnierScreen(navigator = navigator, turniere = turniere)
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
