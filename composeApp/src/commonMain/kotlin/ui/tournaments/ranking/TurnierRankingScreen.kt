package ui.tournaments.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.turnier.TurnierPlatzierung
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.tournaments.TournamentViewModel
import ui.tournaments.details.DetailsScaffold
import ui.util.SectionText


@Composable
fun TurnierRankingScreen(navigator: Navigator, viewModel: TournamentViewModel) {
    val platzierungen = remember { viewModel.aktuellePlatzierungen }

    DetailsScaffold(
        navigator = navigator,
        title = Screen.TurnierRanking.title
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                SectionText("${platzierungen[0].altersKlasse} / ${platzierungen[0].gewichtsKlasse}kg")

                platzierungen.forEach { rank ->
                    RankingCard(rank)
                    Divider()
                }
            }

        }
    }
}

@Composable
fun AllRankingCards() {

}

@Composable
fun RankingCard(rank: TurnierPlatzierung) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F)) {
            MedalRank(rank)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(modifier = Modifier.weight(4F)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                Text(
                    text = rank.ringerName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = rank.verein,
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@Composable
fun MedalRank(rank: TurnierPlatzierung) {
    var medalEmoji = ""
    when (rank.platzierung) {
        "1" -> medalEmoji = "\uD83E\uDD47"
        "2" -> medalEmoji = "\uD83E\uDD48"
        "3" -> medalEmoji = "\uD83E\uDD49"
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = medalEmoji,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = "${rank.platzierung}.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1F)
        )
    }
}