package ui.scoreboard.details

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
import model.scoreboard.details.WrestleStyle

@Composable
fun DetailDropdown(
    modifier: Modifier = Modifier,
    type: String,
    currentValue: String,

    styles: List<WrestleStyle> = listOf(),
    periods: List<Int> = listOf(),
    weights: List<String> = listOf(),

    onSelectStyle: (WrestleStyle) -> Unit = {},
    onSelectPeriod: (Int) -> Unit = {},
    onSelectWeight: (String) -> Unit = {},
) {
    val expanded = remember { mutableStateOf(false) }

    Box {
        TextButton(
            modifier = modifier,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            onClick = { expanded.value = true }
        ) {
            Text(
                text = currentValue,
                style = MaterialTheme.typography.labelMedium
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            when (type) {
                "STYLE" -> {
                    styles.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                onSelectStyle(it)
                                expanded.value = false
                            }
                        )
                    }
                }

                "PERIOD" -> {
                    periods.forEach {
                        DropdownMenuItem(
                            text = { Text("Period $it") },
                            onClick = {
                                onSelectPeriod(it)
                                expanded.value = false
                            }
                        )
                    }
                }

                "WEIGHT" -> {
                    weights.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                onSelectWeight(it)
                                expanded.value = false
                            }
                        )
                    }
                }
            }
        }
    }

}