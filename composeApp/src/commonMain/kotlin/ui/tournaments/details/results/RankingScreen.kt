package ui.tournaments.details.results

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.tournament.Ranking
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import ui.navigation.NavItem
import ui.tournaments.TournamentViewModel
import ui.tournaments.details.DetailsScaffold
import ui.util.SectionText


@Composable
fun RankingScreen(
    navigator: Navigator,
    viewModel: TournamentViewModel = koinInject()
) {
    val rankings = remember { viewModel.selectedRankings }

    DetailsScaffold(
        navigator = navigator,
        title = NavItem.TournamentRanking.title
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
                SectionText("${rankings[0].ageClass} / ${rankings[0].weightClass}")

                rankings.forEach {
                    RankingCard(it)
                    HorizontalDivider()
                }
            }

        }
    }
}

@Composable
fun RankingCard(ranking: Ranking) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F)) {
            MedalRank(ranking)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(modifier = Modifier.weight(4F)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                Text(
                    text = ranking.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ranking.club,
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@Composable
fun MedalRank(ranking: Ranking) {
    var medalEmoji = ""
    when (ranking.rank) {
        "1." -> medalEmoji = "\uD83E\uDD47"
        "2." -> medalEmoji = "\uD83E\uDD48"
        "3." -> medalEmoji = "\uD83E\uDD49"
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
            text = ranking.rank,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1F)
        )
    }
}