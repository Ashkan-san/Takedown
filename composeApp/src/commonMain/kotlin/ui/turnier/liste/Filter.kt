package ui.turnier.liste

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBottomSheet(
    sheetState: SheetState,
    onSheetDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onSheetDismiss()
        },
        sheetState = sheetState
    ) {
        Column {
            val selected = remember { mutableStateOf(false) }
            FilterChip(
                selected = selected.value,
                onClick = { selected.value = !selected.value },
                label = { Text("Kommende Turniere") }
            )
        }
    }
}