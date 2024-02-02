package ui.scoreboard.score

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.scoreboard.score.WrestlerState

@Composable
fun Score(
    red: WrestlerState,
    blue: WrestlerState,
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
            state = red,
            onClickAdd = { onAdd(red) },
            onClickSub = { onSub(red) },
            onClickPenalty = { onPenalty(red) },
            onClickPassive = { onPassive(red) },
            onClickItem = { value -> onClickItem(red, value) }
        )
        ScoreSurface(
            modifier = Modifier.weight(1F),
            reverse = true,
            state = blue,
            onClickAdd = { onAdd(blue) },
            onClickSub = { onSub(blue) },
            onClickPenalty = { onPenalty(blue) },
            onClickPassive = { onPassive(blue) },
            onClickItem = { value -> onClickItem(blue, value) }
        )

    }
}

