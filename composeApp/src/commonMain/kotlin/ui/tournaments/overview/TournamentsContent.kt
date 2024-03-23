@file:JvmName("BbKt")

package ui.tournaments.list

import PullRefreshIndicator
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pullRefresh
import rememberPullRefreshState
import commons.navigation.NavItem
import ui.tournaments.TournamentViewModel

@Composable
fun TournamentsContent(
    viewModel: TournamentViewModel,
    onClickCard: (String) -> Unit,
    onShowFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchQuery = viewModel.searchQuery
    val isLoading = viewModel.isTurniereLoading
    val tournaments = viewModel.tournaments
    val filteredTournaments = tournaments.filter { it.name.contains(searchQuery.value, ignoreCase = true) }
    val refreshState = rememberPullRefreshState(refreshing = isLoading.value, onRefresh = { /*viewModel.updateTurnierList()*/ })

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        SearchFilterBar(
            onSearchChanged = { searchQuery.value = it },
            onShowFilters = onShowFilters,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState),
                //.simpleVerticalScrollbar(lazyListState),
                state = rememberLazyListState()
            ) {
                items(
                    items = filteredTournaments,
                    key = { it.id }
                ) { tournament ->
                    // Zwischenlösung, da clickable UI ständig recomposed
                    val clickableModifier = remember(tournament) {
                        Modifier.clickable {
                            viewModel.selectTournament(tournament)
                            onClickCard(NavItem.TournamentDetails.route)
                        }
                    }

                    TournamentCard(
                        modifier = clickableModifier,
                        tournament = tournament
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = isLoading.value,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}