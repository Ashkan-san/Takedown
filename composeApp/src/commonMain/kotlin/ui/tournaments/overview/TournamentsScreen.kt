package ui.tournaments.overview

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
import commons.ui.TakedownScreen
import org.koin.compose.koinInject
import ui.tournaments.TournamentViewModel
import commons.ui.bottomSheet.CustomBottomSheet
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import takedown.composeapp.generated.resources.Res
import takedown.composeapp.generated.resources.logo

@Composable
fun TournamentsScreen(
    onClickCard: (String) -> Unit,
    navigator: Navigator,
    viewModel: TournamentViewModel = koinInject()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val filterOptions = viewModel.filterOptions
    val selectedOptions = viewModel.selectedOptions

    TakedownScreen(
        title = NavItem.Tournaments.title,
        navigator = navigator
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