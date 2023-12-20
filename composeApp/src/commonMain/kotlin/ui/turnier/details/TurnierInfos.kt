package ui.turnier.details

import Maps
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Turnier
import data.TurnierAlterGewichtKlasse
import ui.util.SectionText
import viewmodel.TurnierViewModel

@Composable
fun TurnierInfos(aktuellesTurnier: Turnier) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        // TURNIER INFOS
        InfoBox(aktuellesTurnier)
        // ALTERS-/GEWICHTSKLASSEN
        AlterGewichtsklasseBox(aktuellesTurnier)
        // MAPS
        //TODO scrolling bug fixen und auf click maps öffnen
        Maps(aktuellesTurnier.adresse)
    }
}

@Composable
fun InfoBox(aktuellesTurnier: Turnier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
    ) {
        Row {
            Text(
                text = aktuellesTurnier.titel,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        DatumZeitText(aktuellesTurnier.datum.datumString)
        AdresseText(aktuellesTurnier.adresse)

        Text(text = "Veranstalter: " + aktuellesTurnier.veranstalter, maxLines = 1)
        Text(text = "Verein: " + aktuellesTurnier.verein, maxLines = 1)
        Divider()
    }

}

@Composable
fun AlterGewichtsklasseBox(aktuellesTurnier: Turnier) {
    SectionText("Alters-/Gewichtsklassen")

    WrestlingStyleCard("Freistil", aktuellesTurnier)
    WrestlingStyleCard("Gr.-röm.", aktuellesTurnier)
}

@Composable
fun DatumZeitText(adresse: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Calendar Icon"
        )
        Text(
            text = adresse,
            maxLines = 1
        )
    }
}

@Composable
fun AdresseText(adresse: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Place Icon"
        )
        Text(
            text = adresse
        )
    }
}

@Composable
fun WrestlingStyleCard(stil: String, aktuellesTurnier: Turnier) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
    ) {
        SectionText(stil)

        aktuellesTurnier.alterGewichtsKlassen.filter { it.stilart == stil }.forEachIndexed { index, details ->
            AltersGewichtsklasseCard(details)
            if (index < aktuellesTurnier.alterGewichtsKlassen.lastIndex) Divider()
        }
    }
}

@Composable
fun AltersGewichtsklasseCard(details: TurnierAlterGewichtKlasse) {
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