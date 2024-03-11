package ui.tournaments.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import ui.tournaments.TournamentViewModel
import ui.util.bottomSheet.CustomBottomSheet

@Composable
fun TournamentsScreen(
    navigator: Navigator,
    viewModel: TournamentViewModel = koinInject()
) {
    val showBottomSheet = remember { viewModel.showBottomSheet }

    val filterOptions = viewModel.filterOptions
    val selectedOptions = viewModel.selectedOptions

    TournamentsScaffold(
        navigator = navigator
    ) { innerPadding ->
        TournamentsList(navigator, viewModel, innerPadding)

        if (showBottomSheet.value) {
            CustomBottomSheet(
                title = "Tournaments Settings",
                //sheetState = sheetState,
                onSheetDismiss = { viewModel.toggleBottomSheet(false) }
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