package ui.scoreboard.punkte

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.scoreboard.PunkteState

@Composable
fun Punkte(
    scoreState: PunkteState,
    onAddBlue: () -> Unit,
    onAddRed: () -> Unit,
    onSubBlue: () -> Unit,
    onSubRed: () -> Unit,
    onPenaltyBlue: () -> Unit,
    onPenaltyRed: () -> Unit,
    onClickItemBlue: (Int) -> Unit,
    onClickItemRed: (Int) -> Unit
) {
    Row(
        modifier = Modifier
    ) {
        PunkteSurface(
            modifier = Modifier.weight(1F),
            color = Color(0xFF0B61A4),
            score = scoreState.scoreBlue,
            penaltyScore = scoreState.penaltyBlue,
            onClickAdd = onAddBlue,
            onClickSub = onSubBlue,
            onClickPenalty = onPenaltyBlue,
            onClickItem = onClickItemBlue
        )
        PunkteSurface(
            modifier = Modifier.weight(1F),
            color = Color(0xFFB72200),
            score = scoreState.scoreRed,
            penaltyScore = scoreState.penaltyRed,
            onClickAdd = onAddRed,
            onClickSub = onSubRed,
            onClickPenalty = onPenaltyRed,
            onClickItem = onClickItemRed
        )
    }
}

