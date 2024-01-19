package ui.scoreboard.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import model.scoreboard.TimerState


@Composable
fun TimerDropdown(
    onClickItem: (TimerState) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val itemList = listOf(
        TimerState("00", "30"),
        TimerState("01", "00"),
        TimerState("01", "30"),
        TimerState("02", "00"),
        TimerState("02", "30"),
        TimerState("03", "00"),
    )

    Box(
        modifier = Modifier
    ) {
        IconButton(
            onClick = { expanded.value = !expanded.value }
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Default.Timer,
                contentDescription = "Dropdown Icon"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            itemList.forEach {
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