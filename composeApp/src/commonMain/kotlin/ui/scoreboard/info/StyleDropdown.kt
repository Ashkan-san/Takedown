package ui.scoreboard.info

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import model.scoreboard.WrestleStyle

@Composable
fun StyleDropdown(
    modifier: Modifier = Modifier,
    styles: List<WrestleStyle>,
    text: String,
    onClickItem: (WrestleStyle) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        TextButton(
            modifier = modifier,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            onClick = { expanded.value = true }
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            styles.forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        onClickItem(it)
                        expanded.value = false
                    }
                )
            }
        }
    }

}