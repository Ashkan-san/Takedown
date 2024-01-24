package ui.scoreboard.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PenaltiesHorizontal(
    penaltyScore: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Passive()
        repeat(3) {
            PenaltyCircle(
                circleNumber = it + 1,
                penaltyScore = penaltyScore
            )
        }
    }
}

@Composable
fun PenaltiesVertical(
    penaltyScore: Int
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        //verticalArrangement = Arrangement.SpaceAround
    ) {
        repeat(3) {
            PenaltyCircle(
                circleNumber = it + 1,
                penaltyScore = penaltyScore
            )
        }
    }
}

