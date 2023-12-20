package ui.turnier.liste

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Turnier
import data.TurnierDatum
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import viewmodel.TurnierViewModel

@Composable
fun TurnierCard(navigator: Navigator, viewModel: TurnierViewModel, turnier: Turnier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            //.height(150.dp)
            .padding(5.dp)
            .clickable {
                viewModel.populateTurnierDetails(turnier)
                navigator.navigate(Screen.TurnierDetails.route)
            },
        //backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1.
            Box(modifier = Modifier.weight(1F)) {
                ParsedDate(turnierDatum = turnier.datum)
            }

            // 2.
            Box(modifier = Modifier.weight(3F)) {
                Column(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    //horizontalAlignment = Alignment.Start,
                    //verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = turnier.titel,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    PlaceText(turnier.stadt)
                }
            }

            // 3.
            Box(
                modifier = Modifier.weight(1F)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    WrestlingStyleBox()
                    AttendeesText(text = "1000")
                }
            }

        }

    }
}

@Composable
fun AttendeesText(text: String) {
    Row {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Person Icon"
        )
        Text(text = text, maxLines = 1)
    }
}

@Composable
fun PlaceText(text: String) {
    Row {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Place Icon"
        )
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun WrestlingStyleBox() {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.onPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "FS",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ParsedDate(turnierDatum: TurnierDatum) {
    Column(
        //modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DateText(turnierDatum.startTag)
        if (turnierDatum.endTag.isNotEmpty()) {
            DateText("-${turnierDatum.endTag}")
        }
        DateText("${getAbbreviatedMonth(turnierDatum.monat)}")
    }
}

@Composable
fun DateText(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

fun getAbbreviatedMonth(month: String): String? {
    return when (month) {
        "01" -> "JAN"
        "02" -> "FEB"
        "03" -> "MAR"
        "04" -> "APR"
        "05" -> "MAY"
        "06" -> "JUN"
        "07" -> "JUL"
        "08" -> "AUG"
        "09" -> "SEP"
        "10" -> "OCT"
        "11" -> "NOV"
        "12" -> "DEC"
        else -> null
    }
}