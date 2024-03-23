package commons.ui

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
import commons.navigation.NavItem
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import takedown.composeapp.generated.resources.Res
import takedown.composeapp.generated.resources.logo

@Composable
fun TakedownScreen(
    title: String,
    navigator: Navigator,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TakedownTopBar(title)
        },
        bottomBar = {
            TakedownBottomBar(
                navigator = navigator
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}


@Composable
fun TakedownTopBar(
    title: String
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Takedown Topbar Icon",
                modifier = Modifier.size(60.dp).padding(start = 10.dp),
                tint = Color.Unspecified,
            )
        },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
    )
}

@Composable
fun TakedownBottomBar(
    navigator: Navigator
) {
    val navItems =
        listOf(
            NavItem.Tournaments,
            NavItem.Fundamentals,
            NavItem.Moves,
            NavItem.Scoreboard,
            NavItem.Account,
        )

    val currentRoute = navigator.currentEntry.collectAsState(null).value?.route?.route

    NavigationBar {
        navItems.forEach { navItem ->
            NavigationBarItem(
                icon = {
                    navItem.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = navItem.title,
                        )
                    }
                },
                label = {
                    Text(
                        text = navItem.title,
                        fontSize = 10.sp,
                        softWrap = false,
                    )
                },
                alwaysShowLabel = true,
                selected = currentRoute == navItem.route,
                colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    if (currentRoute != navItem.route) {
                        navigator.navigate(navItem.route)
                    }
                },
            )
        }
    }
}