package ui.scoreboard.punkte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PunkteButtons(
    onClickAdd: () -> Unit,
    onClickSub: () -> Unit,
    onClickPenalty: () -> Unit,
    onClickItem: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        PunkteDropdown(
            onClickAdd = onClickAdd,
            onClickItem = onClickItem
        )

        IconButton(
            onClick = { onClickSub() }
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Default.Remove,
                contentDescription = "Remove Icon"
            )
        }
        IconButton(
            onClick = { onClickPenalty() }
        ) {
            Text(text = "P", fontSize = 25.sp)
        }
    }
}


