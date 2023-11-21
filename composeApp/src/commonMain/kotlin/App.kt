import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.HomeScreen
import ui.RulesScreen
import ui.navigation.BottomNavItem
import ui.theme.AppTheme

@Composable
fun App() {
    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme(useDarkTheme = false) {
            Navigation(navigator = navigator)
        }
    }
}

@Composable
fun Navigation(navigator: Navigator) {
    NavHost(
        navigator = navigator,
        initialRoute = BottomNavItem.Home.route
    ) {
        // HOME
        scene(route = BottomNavItem.Home.route) {
            HomeScreen(navigator = navigator)
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
