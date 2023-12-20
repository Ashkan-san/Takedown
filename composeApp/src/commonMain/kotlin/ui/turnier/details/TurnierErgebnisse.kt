package ui.turnier.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.Turnier
import data.TurnierPlatzierung
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.util.SectionText
import viewmodel.TurnierViewModel

@Composable
fun TurnierErgebnisse(
    navigator: Navigator,
    aktuellesTurnier: Turnier,
    viewModel: TurnierViewModel
    //groupWrestlers: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        // FILTERN NACH ALTERSKLASSE, DANN NUR PLATZIERUNG 1
        SectionText("\uD83E\uDD47 Sieger \uD83E\uDD47")

        GewichtSiegerCards(navigator, aktuellesTurnier.platzierungen, viewModel)
    }
}

@Composable
fun GewichtSiegerCards(navigator: Navigator, platzierungen: List<TurnierPlatzierung>, viewModel: TurnierViewModel) {
    // NACH ALTER GRUPPIEREN
    val altersGruppen = platzierungen.groupBy { it.altersKlasse }

    altersGruppen.forEach { (alter, platzierungen) ->
        // NACH GEWICHT GRUPPIEREN
        val gewichtsGruppen = platzierungen.groupBy { it.gewichtsKlasse }
        // JEDER SIEGER
        val sieger = gewichtsGruppen.mapValues { (_, value) -> value.first() }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
        ) {
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                SectionText(alter)

                sieger.forEach { (gewicht, platzierung) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                viewModel.updatePlatzierungen(gewichtsGruppen[gewicht]!!)
                                navigator.navigate(Screen.TurnierRanking.route)
                            }
                    ) {
                        Text(
                            text = "${gewicht}kg: ${platzierung.ringerName} (${platzierung.verein})",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SiegerCard() {

}