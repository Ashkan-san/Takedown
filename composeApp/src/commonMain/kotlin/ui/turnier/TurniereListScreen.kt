package ui.turnier

import PullRefreshIndicator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import pullRefresh
import rememberPullRefreshState
import ui.scaffold.MyBottomBar
import ui.scaffold.MyScaffold
import ui.scaffold.MyTopBar
import viewmodel.TurnierViewModel

@Composable
fun TurniereListScreen(
    navigator: Navigator,
    viewModel: TurnierViewModel
) {
    val turniere = remember { viewModel.turniere }
    val isLoading = viewModel.isLoading
    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = viewModel::populateViewModel)

    MyScaffold(
        navigator = navigator
    ) { innerPadding ->
        Box(modifier = Modifier.pullRefresh(refreshState)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when {
                    isLoading.value -> LoadingUi()
                    else -> turniere.forEach { turnier ->
                        TurnierCard(navigator = navigator, viewModel = viewModel, turnier = turnier)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isLoading.value,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        /*LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            //.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when {
                isLoading.value -> item { LoadingUi() }
                else -> items(turniere) { turnier ->
                    println(turnier)
                    TurnierCard(turnier = turnier)
                }
            }
        }*/
    }
}

@Composable
fun LoadingUi() {
    CircularProgressIndicator(
        modifier = Modifier.width(60.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}