package ui.scoreboard.score

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ui.util.buttonModifier

@Composable
fun ScoreDropdown(
    onClickItem: (Int) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val itemList = remember { listOf(1, 2, 3, 4, 5) }

    Box(
        modifier = Modifier
    ) {
        IconButton(
            onClick = { expanded.value = !expanded.value },
        ) {
            Icon(
                modifier = buttonModifier,
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            itemList.forEach {
                DropdownMenuItem(
                    text = { Text("+$it") },
                    onClick = {
                        onClickItem(it)
                        expanded.value = false
                    }
                )
            }
        }
    }
}