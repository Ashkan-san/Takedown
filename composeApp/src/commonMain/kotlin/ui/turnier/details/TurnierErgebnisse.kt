package ui.turnier.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Turnier
import model.TurnierPlatzierung
import ui.util.SectionText

@Composable
fun TurnierErgebnisse(
    aktuellesTurnier: Turnier,
    onCardClick: (TurnierPlatzierung) -> Unit,
    alleSieger: Map<String, List<TurnierPlatzierung>>
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        if (aktuellesTurnier.platzierungen.isNotEmpty()) {
            alleSieger.forEach { (alter, sieger) ->
                AlleSiegerCards(
                    alter = alter,
                    sieger = sieger,
                    onCardClick = onCardClick
                )
            }
        } else {
            SectionText("Keine Ergebnisse")
        }
    }
}

@Composable
fun AlleSiegerCards(
    alter: String,
    sieger: List<TurnierPlatzierung>,
    onCardClick: (TurnierPlatzierung) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "\uD83E\uDD47 $alter",
            fontSize = 30.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(2.dp).fillMaxWidth()
        )
        //Spacer(modifier = Modifier.height(10.dp))

        sieger.forEach { platzierung ->
            SiegerCard(
                winner = platzierung,
                onCardClick = { onCardClick(platzierung) }
            )
            Divider()
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
                text = "${winner.gewichtsKlasse}kg",
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