package ui.tournaments.list

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
import ui.navigation.NavItem
import ui.tournaments.TournamentViewModel

@Composable
fun TournamentsList(
    navigator: Navigator,
    viewModel: TournamentViewModel,
    innerPadding: PaddingValues
) {
    val searchQuery = viewModel.searchQuery
    val isLoading = viewModel.isTurniereLoading
    val tournaments = viewModel.tournaments
    val filteredTournaments = tournaments.filter { it.name.contains(searchQuery.value, ignoreCase = true) }

    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = { /*viewModel.updateTurnierList()*/ })
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
                    .pullRefresh(refreshState),
                //.simpleVerticalScrollbar(lazyListState),
                state = lazyListState
            ) {
                items(
                    items = filteredTournaments,
                    key = {
                        it.id
                    }
                ) {
                    // Zwischenlösung, da clickable UI ständig recomposed
                    val clickableModifier = remember(it) {
                        Modifier.clickable {
                            viewModel.selectTournament(it)
                            navigator.navigate(NavItem.TournamentDetails.route)
                        }
                    }

                    TournamentCard(
                        modifier = clickableModifier,
                        tournament = it
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