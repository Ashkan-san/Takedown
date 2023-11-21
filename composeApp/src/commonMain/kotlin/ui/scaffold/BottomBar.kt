package ui.scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.BottomNavItem

@Composable
fun MyBottomBar(navigator: Navigator) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Rules,
        BottomNavItem.Moves,
        BottomNavItem.Scoreboard,
        BottomNavItem.Account
    )

    NavigationBar() {
        val currentRoute = navigator.currentEntry.collectAsState(null).value?.route?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                        softWrap = false
                    )
                },
                //selectedContentColor = Color.Black,
                //unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,

                selected = currentRoute == item.route,
                onClick = {
                    // Nur wechseln, wenn nicht gleicher Screen
                    if (currentRoute != item.route) {
                        navigator.navigate(item.route)
                    }
                },
            )
        }
    }
}