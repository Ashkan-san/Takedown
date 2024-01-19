package ui.scoreboard.punkte

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.scoreboard.WrestlerState

@Composable
fun Punkte(
    blueState: WrestlerState,
    redState: WrestlerState,
    onAdd: (WrestlerState) -> Unit,
    onSub: (WrestlerState) -> Unit,
    onPenalty: (WrestlerState) -> Unit,
    onClickItem: (WrestlerState, Int) -> Unit
) {
    Row(
        modifier = Modifier
    ) {
        PunkteSurface(
            modifier = Modifier.weight(1F),
            state = blueState,
            onClickAdd = { onAdd(blueState) },
            onClickSub = { onSub(blueState) },
            onClickPenalty = { onPenalty(blueState) },
            onClickItem = { value -> onClickItem(blueState, value) }
        )
        PunkteSurface(
            modifier = Modifier.weight(1F),
            state = redState,
            onClickAdd = { onAdd(redState) },
            onClickSub = { onSub(redState) },
            onClickPenalty = { onPenalty(redState) },
            onClickItem = { value -> onClickItem(redState, value) }
        )
    }
}

