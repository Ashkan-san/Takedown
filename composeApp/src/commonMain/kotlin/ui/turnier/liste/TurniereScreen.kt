package ui.turnier.liste

import PullRefreshIndicator
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import pullRefresh
import rememberPullRefreshState
import ui.navigation.Screen
import ui.util.scaffold.HomeScaffold
import ui.util.simpleVerticalScrollbar
import viewmodel.TurnierViewModel

@Composable
fun TurniereScreen(
    navigator: Navigator,
    viewModel: TurnierViewModel
) {
    HomeScaffold(
        navigator = navigator
    ) { innerPadding ->
        TurnierListe(navigator, viewModel, innerPadding)
    }
}

@Composable
fun TurnierListe(
    navigator: Navigator,
    viewModel: TurnierViewModel,
    innerPadding: PaddingValues
) {
    // Viewmodel Variablen
    val searchQuery = viewModel.searchQuery
    val isLoading = viewModel.isLoading
    val turniere = viewModel.turniere
    val filteredTurniere = turniere.filter { it.titel.contains(searchQuery.value, ignoreCase = true) }

    // BOTTOM SHEET
    // Ob der Sheet hidden, expanded ist usw.
    val sheetState = rememberModalBottomSheetState()
    // Ob der Sheet in der Composition zu sehen ist
    val showBottomSheet = remember { mutableStateOf(false) }
    val filterOptions = viewModel.filterOptions
    val selectedOptions = viewModel.selectedOptions

    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = { viewModel.updateTurnierList() })
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        verticalArrangement = Arrangement.Top
    ) {
        SearchFilterBar(
            onSearchChanged = { searchQuery.value = it },
            onClickFilterButton = { showBottomSheet.value = true },
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState)
                    .simpleVerticalScrollbar(lazyListState),
                state = lazyListState
            ) {
                items(
                    items = filteredTurniere,
                    key = {
                        it.id
                    }
                ) { turnier ->
                    // Zwischenlösung, da clickable UI ständig recomposed
                    val clickableModifier = remember(turnier) {
                        Modifier.clickable {
                            viewModel.updateTurnierDetails(turnier)
                            navigator.navigate(Screen.TurnierDetails.route)
                        }
                    }

                    TurnierCard(
                        modifier = clickableModifier,
                        turnier = turnier
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = isLoading.value,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)//.padding(150.dp)
            )
        }
    }

    if (showBottomSheet.value) {
        TurnierBottomSheet(
            sheetState = sheetState,
            onSheetDismiss = {
                showBottomSheet.value = false
            },
            filterOptions = filterOptions,
            selectedOptions = selectedOptions,
            // TODO jenachdem welcher chip angeklickt anderer filter
            onClickFilterChip = { clickedOption ->
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

