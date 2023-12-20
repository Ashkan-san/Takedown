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
import ui.util.SectionText

@Composable
fun TurnierErgebnisse(
    aktuellesTurnier: Turnier,
    onCardClick: (List<TurnierPlatzierung>) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        SectionText("\uD83E\uDD47 Sieger \uD83E\uDD47")

        GewichtSiegerCards(
            aktuellesTurnier.platzierungen,
            onCardClick = onCardClick
        )
    }
}

@Composable
fun GewichtSiegerCards(
    platzierungen: List<TurnierPlatzierung>,
    onCardClick: (List<TurnierPlatzierung>) -> Unit
) {
    // NACH ALTER GRUPPIEREN
    val altersGruppen = groupByAge(platzierungen)

    altersGruppen.forEach { (alter, platzierungen) ->
        // NACH GEWICHT GRUPPIEREN
        val gewichtsGruppen = groupByWeight(platzierungen)
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
                                gewichtsGruppen[gewicht]?.let { p -> onCardClick(p) }
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

fun groupByAge(platzierungen: List<TurnierPlatzierung>): Map<String, List<TurnierPlatzierung>> {
    return platzierungen.groupBy { it.altersKlasse }
}

fun groupByWeight(platzierungen: List<TurnierPlatzierung>): Map<String, List<TurnierPlatzierung>> {
    return platzierungen.groupBy { it.gewichtsKlasse }
}