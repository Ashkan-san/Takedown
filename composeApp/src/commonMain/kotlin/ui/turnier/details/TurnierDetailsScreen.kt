package ui.turnier.details

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
import ui.util.scaffold.DetailsScaffold
import viewmodel.TurnierViewModel

@Composable
fun TurnierDetailsScreen(navigator: Navigator, viewModel: TurnierViewModel) {
    val aktuellesTurnier = remember { viewModel.aktuellesTurnier }

    val tabTitles = listOf("Infos", "Ergebnisse")
    val pagerState = rememberPagerState { tabTitles.size }

    DetailsScaffold(
        navigator = navigator,
        title = Screen.TurnierDetails.title
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

            TurnierDetailsTabRow(pagerState, tabTitles)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().weight(1F)
            ) { index ->
                // TODO Daten erst anzeigen, wenn geladen
                when (index) {
                    0 -> TurnierInfos(
                        aktuellesTurnier = aktuellesTurnier.value!!,
                        location = viewModel.locationState.value,
                        onUpdateLocation = { lat, lng ->
                            viewModel.updateLocation(lat, lng)
                        },
                        isMapLoaded = viewModel.isMapLoaded.value,
                        onMapLoaded = { viewModel.setMapLoaded() }
                    )

                    1 -> TurnierErgebnisse(
                        aktuellesTurnier = aktuellesTurnier.value!!,
                        onCardClick = { updatedPlatzierungen ->
                            viewModel.updatePlatzierungen(updatedPlatzierungen)
                            navigator.navigate(Screen.TurnierRanking.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TurnierDetailsTabRow(pagerState: PagerState, tabTitles: List<String>) {
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





