package ui.tournaments.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import model.tournament.Tournament
import model.tournament.TournamentDate


@Composable
fun TournamentCard(
    modifier: Modifier = Modifier,
    tournament: Tournament
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            //.height(120.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        /*.clickable {
            onClickCard(turnier)
        },*/
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1.
            Box(modifier = Modifier.fillMaxHeight().weight(1F)) {
                ParsedDate(date = tournament.date!!)
            }

            // 2.
            Box(modifier = Modifier.fillMaxHeight().weight(3F)) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = tournament.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        //fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(5.dp))
                    VenueText(tournament.venue)
                }
            }

            /*// 3.
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
            }*/

        }

    }
}

@Composable
fun ParsedDate(date: TournamentDate) {
    Column(
        modifier = Modifier.fillMaxHeight().padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DateText("${getAbbreviatedMonth(date.month)}")

        if (date.endDay.isNotEmpty()) {
            DateText("${date.startDay}-${date.endDay}")
        } else {
            DateText(date.startDay)
        }
    }
}


@Composable
fun DateText(text: String) {
    Text(
        text = text,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun VenueText(text: String) {
    Row {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location Icon"
        )
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
fun AttendeesText(text: String) {
    Row {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Person Icon"
        )
        Text(text = text, maxLines = 1)
    }
}
