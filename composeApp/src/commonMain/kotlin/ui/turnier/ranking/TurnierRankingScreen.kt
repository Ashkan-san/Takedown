package ui.turnier.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.TurnierPlatzierung
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.util.SectionText
import ui.util.scaffold.DetailsScaffold
import viewmodel.TurnierViewModel


@Composable
fun TurnierRankingScreen(navigator: Navigator, viewModel: TurnierViewModel) {
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
            val platzierungen = remember { viewModel.aktuellePlatzierungen }

            SectionText("${platzierungen[0].altersKlasse} / ${platzierungen[0].gewichtsKlasse}kg")

            platzierungen.forEachIndexed { index, rank ->
                RankingCard(rank)
            }
        }
    }
}

@Composable
fun RankingCard(rank: TurnierPlatzierung) {
    Card {
        Row {
            // RANK
            var medalEmoji = ""
            when (rank.platzierung) {
                "1" -> medalEmoji = "\uD83E\uDD47"
                "2" -> medalEmoji = "\uD83E\uDD48"
                "3" -> medalEmoji = "\uD83E\uDD49"
            }
            Text(text = "$medalEmoji ${rank.platzierung}. ")
            // NAME
            Text(text = "${rank.ringerName} ")
            // CLUB
            Text(text = "${rank.verein} ")
        }
    }
}
