package ui.turnier.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        if (aktuellesTurnier.platzierungen.isNotEmpty()) {
            SectionText("\uD83E\uDD47 Sieger \uD83E\uDD47")

            AlleSiegerCards(
                aktuellesTurnier.platzierungen,
                onCardClick = onCardClick
            )
        } else {
            SectionText("Keine Ergebnisse")
        }

    }
}

@Composable
fun AlleSiegerCards(
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

        Column {
            SectionText(alter)

            // TODO divider nur bis ende wenn ganze sektionen abschließt, sonst nicht
            sieger.forEach { (gewicht, platzierung) ->
                SiegerCard(
                    winner = platzierung,
                    onCardClick = {
                        gewichtsGruppen[gewicht]?.let { p -> onCardClick(p) }
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
fun SiegerCard(
    winner: TurnierPlatzierung,
    onCardClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onCardClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F)) {
            Text(
                text = "${winner.gewichtsKlasse}kg:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(modifier = Modifier.weight(4F)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                Text(
                    text = winner.ringerName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = winner.verein,
                    fontSize = 15.sp,
                )
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