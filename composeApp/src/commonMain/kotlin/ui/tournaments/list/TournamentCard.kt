package ui.tournaments.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import data.model.tournament.Tournament
import data.model.tournament.TournamentDate


@Composable
fun TournamentCard(
    modifier: Modifier = Modifier,
    tournament: Tournament
) {
    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1.
            Box(modifier = Modifier.weight(1f)) {
                ParsedDate(date = tournament.date!!)
            }

            Spacer(Modifier.width(5.dp))


            // 2.
            Box(modifier = Modifier.weight(2.5f)) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = tournament.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        //fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(5.dp))
                    VenueText(tournament.venue)
                }
            }
            Spacer(Modifier.width(5.dp))

            // 3.

            Box(modifier = Modifier) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(tournament.club!!.image)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp),
                    contentDescription = ""
                )
            }
        }

    }
}

@Composable
fun ParsedDate(date: TournamentDate) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateText(date.getAbbreviatedMonth())

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
        maxLines = 1,
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
