package ui.turnier

import Maps
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Turnier
import data.TurnierAlterGewichtKlasse
import data.TurnierDatum

@Composable
fun TurnierDetailInfos(aktuellesTurnier: Turnier) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // TITEL UND DATUM
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // TODO evt noch datum daneben
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
        }
    }

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
    WrestlingStyleCard("Freistil", aktuellesTurnier)
    WrestlingStyleCard("Gr.-röm.", aktuellesTurnier)

    // MAPS
    Maps(aktuellesTurnier.adresse)
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