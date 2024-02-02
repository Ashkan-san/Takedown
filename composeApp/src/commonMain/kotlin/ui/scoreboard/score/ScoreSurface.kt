package ui.scoreboard.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import model.scoreboard.score.WrestlerState

@Composable
fun ScoreSurface(
    modifier: Modifier = Modifier,
    reverse: Boolean,
    state: WrestlerState,
    onClickAdd: () -> Unit,
    onClickSub: () -> Unit,
    onClickPenalty: () -> Unit,
    onClickPassive: () -> Unit,
    onClickItem: (Int) -> Unit,
) {
    val direction = if (reverse) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        Surface(
            modifier = modifier.fillMaxHeight(),
            color = state.score.color,
            contentColor = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                WinnerIcon(state.isWinner)
                //NameText()

                // todo fix dass sich elemente nach oben verschieben, wenn textgröße sich ändert
                ScoreText(
                    text = "${state.score.scoreValue}",
                    onClick = onClickAdd
                )

                PenaltiesHorizontal(
                    penaltyScore = state.penalty,
                    isPassive = state.isPassive,
                    onClickPassive = onClickPassive
                )

                ScoreButtons(
                    onClickSub = onClickSub,
                    onClickPenalty = onClickPenalty,
                    onClickItem = onClickItem
                )
            }
        }
    }
}