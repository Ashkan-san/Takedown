package ui.tournaments.details.results

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.tournament.Ranking
import commons.ui.SectionText

@Composable
fun TournamentResults(
    winnersByAge: Map<String, List<Ranking>>,
    onCardClick: (Ranking) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        if (winnersByAge.isNotEmpty()) {
            winnersByAge.forEach { (ageClass, winners) ->
                AllWinnerCards(
                    ageClass = ageClass,
                    winners = winners,
                    onCardClick = onCardClick
                )
            }
        } else {
            SectionText("No results available")
        }
    }
}

@Composable
fun AllWinnerCards(
    ageClass: String,
    winners: List<Ranking>,
    onCardClick: (Ranking) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "\uD83E\uDD47 $ageClass",
            fontSize = 30.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(2.dp).fillMaxWidth()
        )
        //Spacer(modifier = Modifier.height(10.dp))

        winners.forEach {
            WinnerContainer(
                winner = it,
                onCardClick = onCardClick
            )
            HorizontalDivider()
        }
    }
}

// TODO ändern dass es schöner ist und anclickbar aussieht, mit mehr Platz pro Winner
@Composable
fun WinnerContainer(
    winner: Ranking,
    onCardClick: (Ranking) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onCardClick(winner)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F)) {
            Text(
                text = winner.weightClass,
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
                    text = winner.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = winner.club,
                    fontSize = 15.sp,
                )
            }
        }
    }
}