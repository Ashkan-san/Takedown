package ui.turnier

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Turnier
import data.TurnierAlterGewichtKlasse
import getTurnierBild
import moe.tlaster.precompose.navigation.Navigator
import viewmodel.TurnierViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                title = { Text("Turnier Details") }
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
                painter = getTurnierBild(),
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

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                when (tabState.value) {
                    0 -> TurnierDetailInfos(aktuellesTurnier.value!!)
                    1 -> TurnierDetailErgebnisse()
                }
            }

        }
    }
}

// TODO in kleine Methoden splitten
@Composable
fun TurnierDetailInfos(aktuellesTurnier: Turnier) {
    // TURNIER INFOS

    // ALTERS-/GEWICHTSKLASSEN
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().padding(5.dp)
    ) {
        Text(
            text = "Alters-/Gewichtsklassen",
            fontSize = 20.sp
        )
    }
    KlassenCard("Freistil", aktuellesTurnier)
    KlassenCard("Gr.-röm.", aktuellesTurnier)

    // MAPS
}

@Composable
fun KlassenCard(stil: String, aktuellesTurnier: Turnier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stil,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        aktuellesTurnier.alterGewichtsKlassen.filter { it.stilart == stil }.forEachIndexed { index, details ->
            DetailsCard(details)
            if (index < aktuellesTurnier.alterGewichtsKlassen.lastIndex) Divider()
        }
    }
}

@Composable
fun DetailsCard(details: TurnierAlterGewichtKlasse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // GESCHLECHT
            Box(modifier = Modifier.weight(1F)) {
                details.geschlecht.forEach { geschlecht ->
                    when (geschlecht) {
                        "Männlich" -> Icon(imageVector = Icons.Default.Male, contentDescription = "Männlich Icon", modifier = Modifier.size(50.dp))
                        "Weiblich" -> Icon(imageVector = Icons.Default.Female, contentDescription = "Weiblich Icon", modifier = Modifier.size(50.dp))
                    }
                }
            }

            // ALTERSKLASSE, JAHRGÄNGE
            Box(modifier = Modifier.weight(4F)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = details.altersKlasse,
                        //fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Text(
                        text = details.jahrgaenge,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }

            // STIL
            Box(modifier = Modifier.weight(4F)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // GEWICHTSKLASSEN
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val gewichtsKlassenText = details.gewichtsKlassen.joinToString(", ") { gewicht ->
                            gewicht
                        }
                        Text(
                            text = gewichtsKlassenText,
                            //textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun TurnierDetailErgebnisse() {

}

