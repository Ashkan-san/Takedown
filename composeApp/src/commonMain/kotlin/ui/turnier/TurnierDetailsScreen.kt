package ui.turnier

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
            TopAppBar(
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
    Text(text = "${aktuellesTurnier.adresse} ${aktuellesTurnier.land}")

    // ALTERS-/GEWICHTSKLASSEN
    Text(text = "Alters-/Gewichtsklassen:")

    Text(text = "Freistil")
    aktuellesTurnier.alterGewichtsKlassen.filter { it.stilart == "Freistil" }.forEach { details ->
        AlterGewichtKlassen(details)
    }
    Text(text = "Greco")
    aktuellesTurnier.alterGewichtsKlassen.filter { it.stilart == "Gr.-röm." }.forEach { details ->
        AlterGewichtKlassen(details)
    }

    // MAPS
}

@Composable
fun AlterGewichtKlassen(details: TurnierAlterGewichtKlasse) {
    Row() {
        // ALTERSKLASSE, JAHRGÄNGE
        Column() {
            Text(text = details.altersKlasse)
            Text(text = details.jahrgaenge)
        }

        // GESCHLECHT, STIL
        Column() {
            Row() {
                details.geschlecht.forEach { geschlecht ->
                    when (geschlecht) {
                        "Männlich" -> Icon(imageVector = Icons.Default.Male, contentDescription = "Männlich Icon")
                        "Weiblich" -> Icon(imageVector = Icons.Default.Female, contentDescription = "Weiblich Icon")
                    }
                }
                Text(text = details.modus)
            }

            // GEWICHTSKLASSEN
            Row() {
                val gewichtsKlassenText = details.gewichtsKlassen.joinToString(", ") { gewicht ->
                    gewicht
                }
                Text(text = gewichtsKlassenText)
            }
        }


    }
}

@Composable
fun TurnierDetailErgebnisse() {

}

