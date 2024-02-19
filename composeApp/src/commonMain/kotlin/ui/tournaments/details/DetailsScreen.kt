package ui.tournaments.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.tournaments.TournamentViewModel
import ui.tournaments.details.info.TournamentInfo
import ui.tournaments.details.results.TournamentResults

@Composable
fun DetailsScreen(navigator: Navigator, viewModel: TournamentViewModel) {
    val selectedTournament = remember { viewModel.selectedTournament }

    val tabTitles = listOf("Information", "Results")
    val pagerState = rememberPagerState { tabTitles.size }

    DetailsScaffold(
        navigator = navigator,
        title = Screen.TournamentDetails.title
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO später wieder hinzufügen
            /*Image(
                painter = painterResource("jordan.jpg"),
                contentDescription = "Turnier Bild",
                //modifier = Modifier.size
            )*/

            TournamentDetailsTabRow(pagerState, tabTitles)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().weight(1F)
            ) { index ->
                when (index) {
                    // TODO MAP UPDATEN
                    0 -> TournamentInfo(
                        tournament = selectedTournament.value!!,
                        location = viewModel.locationState.value,
                        onUpdateLocation = { lat, lng ->
                            viewModel.updateLocation(lat, lng)
                        },
                        isMapLoaded = viewModel.isMapLoaded.value,
                        onMapLoaded = { viewModel.setMapLoaded() }
                    )

                    1 -> TournamentResults(
                        winnersByAge = viewModel.getWinnersByAge(),
                        onCardClick = {
                            viewModel.setRankings(it)
                            navigator.navigate(Screen.TournamentRanking.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TournamentDetailsTabRow(pagerState: PagerState, tabTitles: List<String>) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(selectedTabIndex = pagerState.currentPage) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                },
                text = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
            )
        }
    }
}





