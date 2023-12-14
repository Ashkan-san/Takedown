package ui.turnier

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import viewmodel.TurnierViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun TurnierDetailsScreen(navigator: Navigator, viewModel: TurnierViewModel) {
    val aktuellesTurnier = remember { viewModel.aktuellesTurnier }

    val tabState = remember { mutableStateOf(0) }
    val tabTitles = listOf("Infos", "Ergebnisse")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() },
                        //modifier = Modifier.size(100.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Top Bar Back Icon",
                            //modifier = Modifier.size(100.dp),
                            //tint = Color.Unspecified
                        )
                    }
                },
                title = {
                    Text(
                        text = "Turnier Details",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource("jordan.jpg"),
                contentDescription = "Turnier Bild",
                //modifier = Modifier.size
            )

            TabRow(selectedTabIndex = tabState.value) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState.value == index,
                        onClick = { tabState.value = index },
                        text = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
                    )
                }
            }

            // TODO Daten erst anzeigen, wenn geladen
            when (tabState.value) {
                0 -> TurnierDetailsInfos(aktuellesTurnier.value!!)
                1 -> TurnierDetailsErgebnisse(aktuellesTurnier.value!!)
            }
        }
    }
}





