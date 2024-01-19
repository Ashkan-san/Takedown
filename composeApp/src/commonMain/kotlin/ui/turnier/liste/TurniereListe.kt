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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import pullRefresh
import rememberPullRefreshState
import ui.navigation.Screen
import ui.turnier.TurnierViewModel
import ui.util.simpleVerticalScrollbar

@Composable
fun TurniereListe(
    navigator: Navigator,
    viewModel: TurnierViewModel,
    innerPadding: PaddingValues
) {
    val searchQuery = viewModel.searchQuery
    val isLoading = viewModel.isTurniereLoading
    val turniere = viewModel.turniere
    val filteredTurniere = turniere.filter { it.titel.contains(searchQuery.value, ignoreCase = true) }

    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = { viewModel.updateTurnierList() })
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        verticalArrangement = Arrangement.Top
    ) {
        SearchFilterBar(
            onSearchChanged = { searchQuery.value = it },
            onClickFilterButton = { viewModel.toggleBottomSheet(true) },
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


}