package ui.scoreboard.punkte

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.util.CustomIconButton

@Composable
fun PunkteDropdown(
    onClickAdd: () -> Unit,
    onClickItem: (Int) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val itemList = listOf(1, 2, 3, 4)

    Box(
        modifier = Modifier
    ) {
        CustomIconButton(
            onClick = { onClickAdd() },
            onLongClick = { expanded.value = !expanded.value }
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
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