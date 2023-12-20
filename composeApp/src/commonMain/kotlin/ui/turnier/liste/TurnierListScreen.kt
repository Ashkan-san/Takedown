package ui.turnier.liste

import PullRefreshIndicator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import pullRefresh
import rememberPullRefreshState
import ui.util.scaffold.HomeScaffold
import viewmodel.TurnierViewModel

@Composable
fun TurniereListScreen(
    navigator: Navigator,
    viewModel: TurnierViewModel
) {
    val isLoading = viewModel.isLoading

    HomeScaffold(
        navigator = navigator
    ) { innerPadding ->
        when {
            isLoading.value -> LoadingUi(innerPadding)
            else -> TurnierListe(navigator, viewModel, innerPadding)
        }
    }
}

@Composable
fun TurnierListe(
    navigator: Navigator,
    viewModel: TurnierViewModel,
    innerPadding: PaddingValues
) {
    val isLoading = viewModel.isLoading
    val turniere = remember { viewModel.turniere }
    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = viewModel::populateViewModel)

    Box(modifier = Modifier.pullRefresh(refreshState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            turniere.forEach { turnier ->
                TurnierCard(navigator = navigator, viewModel = viewModel, turnier = turnier)
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading.value,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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