package ui.turnier.details

import Maps
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.MyLatLng
import data.RingenKlasse
import data.Turnier
import ui.util.SectionText

@Composable
fun TurnierInfos(
    aktuellesTurnier: Turnier,
    location: MyLatLng?,
    onUpdateLocation: (Double, Double) -> Unit,
    isMapLoaded: Boolean,
    onMapLoaded: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        // TURNIER INFOS
        InfoBox(aktuellesTurnier)
        // ALTERS-/GEWICHTSKLASSEN
        RingenKlasseBox(aktuellesTurnier)
        // MAPS
        Maps(
            turnier = aktuellesTurnier,
            location = location!!,
            onUpdateLocation = { lat, lng -> onUpdateLocation(lat, lng) },
            isMapLoaded = isMapLoaded,
            onMapLoaded = { onMapLoaded() }
        )
        //Maps(aktuellesTurnier)
    }
}

@Composable
fun InfoBox(aktuellesTurnier: Turnier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(
                text = aktuellesTurnier.titel,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        Text(text = "Veranstalter: " + aktuellesTurnier.veranstalter, maxLines = 1)
        Text(text = "Verein: " + aktuellesTurnier.verein, maxLines = 1)

        DatumZeitText(aktuellesTurnier.datum.datumString)
        AdresseText(aktuellesTurnier.adresse)

        Divider(modifier = Modifier.padding(vertical = 5.dp))
    }

}

@Composable
fun RingenKlasseBox(aktuellesTurnier: Turnier) {
    SectionText("Alters-/Gewichtsklassen")
    Spacer(modifier = Modifier.height(10.dp))
    RingenStil("Freistil", aktuellesTurnier)
    Spacer(modifier = Modifier.height(10.dp))
    RingenStil("Gr.-röm.", aktuellesTurnier)
    Spacer(modifier = Modifier.height(10.dp))
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
fun RingenStil(stil: String, aktuellesTurnier: Turnier) {
    val filteredKlassen = filterKlassenNachStil(aktuellesTurnier, stil)

    if (filteredKlassen.isNotEmpty()) {
        Text(
            text = stil,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        filteredKlassen.forEachIndexed { index, details ->
            RingenKlasse(details)
            if (index < filteredKlassen.lastIndex) Divider(modifier = Modifier.padding(5.dp))
        }
    }
}

private fun filterKlassenNachStil(aktuellesTurnier: Turnier, stil: String): List<RingenKlasse> {
    return aktuellesTurnier.alterGewichtsKlassen.filter { it.stilart == stil }
}

@Composable
fun RingenKlasse(details: RingenKlasse) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(2.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // GESCHLECHT
        Box(modifier = Modifier.weight(3F)) {
            Row {
                details.geschlecht.forEach { geschlecht ->
                    GeschlechtIcon(geschlecht)
                }
            }
        }

        // ALTERSKLASSE, JAHRGÄNGE
        Box(modifier = Modifier.weight(4F)) {
            Column {
                Text(
                    text = details.altersKlasse,
                    fontWeight = FontWeight.Bold,
                    //textAlign = TextAlign.Center,
                    //maxLines = 2
                )
                Text(
                    text = details.jahrgaenge,
                    //textAlign = TextAlign.Center,
                )
            }
        }

        Spacer(Modifier.width(5.dp))

        // STIL
        Box(modifier = Modifier.weight(4F)) {
            // GEWICHTSKLASSEN
            Row {
                val gewichtsKlassenText = details.gewichtsKlassen.joinToString(", ") { gewicht ->
                    gewicht
                }
                Text(
                    text = gewichtsKlassenText
                )
            }
        }
    }
}

@Composable
fun GeschlechtIcon(gender: String) {
    when (gender) {
        "Männlich" -> Icon(imageVector = Icons.Default.Male, contentDescription = "Männlich Icon", modifier = Modifier.size(40.dp))
        "Weiblich" -> Icon(imageVector = Icons.Default.Female, contentDescription = "Weiblich Icon", modifier = Modifier.size(40.dp))
    }
}