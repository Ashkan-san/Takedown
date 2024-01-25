package ui.scoreboard.score

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.scoreboard.WrestlerState

@Composable
fun Score(
    redState: WrestlerState,
    blueState: WrestlerState,
    onAdd: (WrestlerState) -> Unit,
    onSub: (WrestlerState) -> Unit,
    onPenalty: (WrestlerState) -> Unit,
    onPassive: (WrestlerState) -> Unit,
    onClickItem: (WrestlerState, Int) -> Unit
) {
    Row(
        modifier = Modifier
    ) {
        ScoreSurface(
            modifier = Modifier.weight(1F),
            reverse = false,
            state = redState,
            onClickAdd = { onAdd(redState) },
            onClickSub = { onSub(redState) },
            onClickPenalty = { onPenalty(redState) },
            onClickPassive = { onPassive(redState) },
            onClickItem = { value -> onClickItem(redState, value) }
        )
        ScoreSurface(
            modifier = Modifier.weight(1F),
            reverse = true,
            state = blueState,
            onClickAdd = { onAdd(blueState) },
            onClickSub = { onSub(blueState) },
            onClickPenalty = { onPenalty(blueState) },
            onClickPassive = { onPassive(blueState) },
            onClickItem = { value -> onClickItem(blueState, value) }
        )

    }
}

