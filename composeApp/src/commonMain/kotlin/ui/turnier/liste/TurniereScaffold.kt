package ui.turnier.liste

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import ui.navigation.BottomNavItem

@Composable
fun TurniereScaffold(
    navigator: Navigator,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TurniereTopSearchBar(BottomNavItem.Turniere.title)
            //HomeTopBar(BottomNavItem.Turniere.title)
        },
        bottomBar = {
            TurniereBottomBar(navigator = navigator)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun HomeTopBar(title: String) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource("logo.png"),
                contentDescription = "Top Bar Icon",
                modifier = Modifier.size(70.dp).padding(start = 10.dp),
                tint = Color.Unspecified
            )
        },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
fun TurniereTopSearchBar(title: String) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource("logo.png"),
                contentDescription = "Top Bar Icon",
                modifier = Modifier.size(60.dp).padding(start = 10.dp),
                tint = Color.Unspecified
            )
        },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        }
    )
}

@Composable
fun TurniereBottomBar(navigator: Navigator) {
    val items = listOf(
        BottomNavItem.Turniere,
        BottomNavItem.Rules,
        BottomNavItem.Moves,
        BottomNavItem.Scoreboard,
        BottomNavItem.Account
    )

    NavigationBar() {
        val currentRoute = navigator.currentEntry.collectAsState(null).value?.route?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        softWrap = false
                    )
                },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.primaryContainer),
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