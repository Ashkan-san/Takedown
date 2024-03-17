package ui.tournaments.list

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import commons.navigation.NavItem
import org.koin.compose.koinInject
import ui.tournaments.TournamentViewModel
import commons.ui.bottomSheet.CustomBottomSheet
import org.jetbrains.compose.resources.painterResource
import takedown.composeapp.generated.resources.Res
import takedown.composeapp.generated.resources.logo

@Composable
fun TournamentsScreen(
    currentRoute: String?,
    onClickNavBarItem: (String) -> Unit,
    onClickCard: (String) -> Unit,
    viewModel: TournamentViewModel = koinInject()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val filterOptions = viewModel.filterOptions
    val selectedOptions = viewModel.selectedOptions

    Scaffold(
        topBar = { TournamentTopSearchBar(NavItem.Tournaments.title) },
        bottomBar = {
            TournamentBottomBar(
                currentRoute = currentRoute,
                onClickNavBarItem = onClickNavBarItem
            )
        }
    ) { innerPadding ->
        TournamentsContent(
            // todo statt viewmodel daten übergeben evt
            viewModel = viewModel,
            onClickCard = onClickCard,
            onShowFilters = { showBottomSheet = true },
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        )

        if (showBottomSheet) {
            CustomBottomSheet(
                title = "Tournaments Settings",
                onSheetDismiss = { showBottomSheet = false }
            ) {
                FilterChips(
                    filterOptions = filterOptions,
                    selectedOptions = selectedOptions,
                    onClickFilterChip = { clickedOption ->
                        // TODO
                        when (clickedOption) {
                            "FilterOption1" -> {
                            }

                            "FilterOption2" -> {
                            }

                            else -> {
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TournamentTopSearchBar(
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
fun TournamentBottomBar(
    currentRoute: String?,
    onClickNavBarItem: (String) -> Unit,
) {
    val navItems =
        listOf(
            NavItem.Tournaments,
            NavItem.Fundamentals,
            NavItem.Moves,
            NavItem.Scoreboard,
            NavItem.Account,
        )

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
                        onClickNavBarItem(navItem.route)
                    }
                },
            )
        }
    }
}