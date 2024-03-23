package ui.tournaments.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FilterChips(
    filterOptions: List<String>,
    selectedOptions: MutableMap<String, Boolean>,
    onClickFilterChip: (String) -> Unit
) {
    Column {
        filterOptions.forEach { option ->
            FilterChip(
                selected = selectedOptions[option] ?: false,
                onClick = {
                    selectedOptions[option] = !(selectedOptions[option] ?: false)
                    onClickFilterChip(option)
                },
                label = { Text(option) }
            )
        }
    }
}