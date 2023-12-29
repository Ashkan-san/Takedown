package ui.turnier.liste

import PullRefreshIndicator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
fun TurniereListScreen(
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

    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = { viewModel.updateTurnierList() })
    val lazyListState = rememberLazyListState()

    val filteredTurniere = viewModel.turniere.filter { it.titel.contains(searchQuery.value, ignoreCase = true) }

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        verticalArrangement = Arrangement.Top
    ) {
        SearchFilterRow(
            onSearchChanged = { searchQuery.value = it }
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
                    TurnierCard(
                        turnier = turnier,
                        onClickCard = {
                            viewModel.updateTurnierDetails(turnier)
                            navigator.navigate(Screen.TurnierDetails.route)
                        }
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

@Composable
fun LoadingUi(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(60.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}