package ui.turnier.liste

import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import moe.tlaster.precompose.navigation.Navigator
import ui.turnier.TurnierViewModel
import ui.util.bottomSheet.CustomBottomSheet

@Composable
fun TurniereScreen(
    navigator: Navigator,
    viewModel: TurnierViewModel
) {
    // Ob der Sheet hidden, expanded ist usw. und ob der Sheet in der Composition zu sehen ist
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { viewModel.showBottomSheet }

    val filterOptions = viewModel.filterOptions
    val selectedOptions = viewModel.selectedOptions

    TurniereScaffold(
        navigator = navigator
    ) { innerPadding ->
        TurniereListe(navigator, viewModel, innerPadding)

        if (showBottomSheet.value) {
            CustomBottomSheet(
                title = "Turnierliste Settings",
                sheetState = sheetState,
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