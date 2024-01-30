package ui.scoreboard.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import model.scoreboard.WrestlerState
import ui.util.iconButtonModifier

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
            color = state.color,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    modifier = iconButtonModifier,
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "",
                    tint = if (state.isWinner) Color.Yellow else Color.Transparent
                )
                //NameText()

                // todo fix dass sich elemente nach oben verschieben, wenn textgröße sich ändert
                ScoreText(
                    text = "${state.score}",
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