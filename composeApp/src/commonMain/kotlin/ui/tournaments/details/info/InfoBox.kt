package ui.tournaments.details.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.tournament.Tournament
import model.tournament.TournamentDate

@Composable
fun InfoBox(tournament: Tournament) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(
                text = tournament.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        Text(text = "Host: " + tournament.host, maxLines = 1)
        LinkText(tournament.club!!.name, tournament.club!!.website)

        DatumZeitText(tournament.date!!)
        VenueText(tournament.venue)

        HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))
    }
}

@Composable
fun LinkText(linkText: String, link: String) {
    // Club als Link
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = LocalContentColor.current.copy())) {
            append("Club: ")
        }

        pushStringAnnotation(tag = "link", annotation = link)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(linkText)
        }
        pop()
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        maxLines = 1,
        style = LocalTextStyle.current.copy(),
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "link",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        }
    )
}

@Composable
fun DatumZeitText(date: TournamentDate) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Calendar Icon"
        )
        Text(
            text = date.toString(),
            maxLines = 1
        )
    }
}

@Composable
fun VenueText(venue: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Place Icon"
        )
        Text(
            text = venue
        )
    }
}