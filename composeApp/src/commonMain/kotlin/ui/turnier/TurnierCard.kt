package ui.turnier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun TurnierCard(turnier: Turnier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(5.dp),
        //backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
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
                    modifier = Modifier.padding(horizontal = 10.dp),
                    //horizontalAlignment = Alignment.Start,
                    //verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = turnier.titel,
                        //fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        //modifier = Modifier.fillMaxWidth(),
                        //verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Place Icon"
                        )
                        Text(
                            text = turnier.ort,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // 3.
            Box(
                modifier = Modifier.weight(1F)
                //contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    WrestlingStyleBox()
                    Row(
                        //verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Place Icon"
                        )
                        Text(text = "160", maxLines = 1)
                    }
                }
            }

        }

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
        Text(
            text = turnierDatum.startTag,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        if (turnierDatum.endTag.isNotEmpty()) {
            Text(
                text = "-${turnierDatum.endTag}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "${getAbbreviatedMonth(turnierDatum.monat)}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        /*Text(
            text = "$abbreviatedYear",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )*/
    }
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