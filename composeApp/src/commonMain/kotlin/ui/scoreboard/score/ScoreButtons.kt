package ui.scoreboard.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import commons.ui.iconButtonModifier

@Composable
fun ScoreButtons(
    onClickSub: () -> Unit,
    onClickPenalty: () -> Unit,
    onClickItem: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = { onClickPenalty() }
        ) {
            Icon(
                modifier = iconButtonModifier,
                imageVector = Icons.Default.FlagCircle,
                contentDescription = "Penalty Icon"
            )
        }

        ScoreDropdown(
            onClickItem = onClickItem
        )

        IconButton(
            onClick = { onClickSub() }
        ) {
            Icon(
                modifier = iconButtonModifier,
                imageVector = Icons.Default.Remove,
                contentDescription = "Remove Icon"
            )
        }
    }
}


