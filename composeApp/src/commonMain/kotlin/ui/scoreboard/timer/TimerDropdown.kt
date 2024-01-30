package ui.scoreboard.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import model.scoreboard.TimerState
import ui.util.buttonModifier


@Composable
fun TimerDropdown(
    timerList: List<TimerState>,
    onClickItem: (TimerState) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
    ) {
        IconButton(
            onClick = { expanded.value = !expanded.value }
        ) {
            Icon(
                modifier = buttonModifier,
                imageVector = Icons.Default.Timer,
                contentDescription = "Dropdown Icon"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            timerList.forEach {
                DropdownMenuItem(
                    text = { Text(it.toString()) },
                    onClick = {
                        onClickItem(it)
                        expanded.value = false
                    }
                )
            }
        }
    }
}