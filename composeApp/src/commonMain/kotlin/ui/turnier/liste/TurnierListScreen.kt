package ui.turnier.liste

import PullRefreshIndicator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import pullRefresh
import rememberPullRefreshState
import ui.navigation.Screen
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

    // Was wir suchen
    val searchQuery = remember { mutableStateOf("") }
    val filteredTurniere = turniere.filter { it.titel.contains(searchQuery.value, ignoreCase = true) }

    Box(modifier = Modifier.pullRefresh(refreshState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            SearchBar(
                onSearch = { query -> searchQuery.value = query }
            )

            filteredTurniere.forEach { turnier ->
                TurnierCard(
                    turnier = turnier,
                    onClickCard = {
                        viewModel.populateTurnierDetails(turnier)
                        navigator.navigate(Screen.TurnierDetails.route)
                    }
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

@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onSearch(it.text)
            },

            placeholder = { Text(text = "Turnier suchen") },
            maxLines = 1,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            singleLine = true,

            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (textState.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            textState.value = TextFieldValue("")
                            onSearch("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cancel",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            }
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