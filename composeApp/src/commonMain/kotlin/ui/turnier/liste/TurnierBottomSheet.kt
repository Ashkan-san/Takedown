package ui.turnier.liste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun TurnierBottomSheet(
    sheetState: SheetState,
    onSheetDismiss: () -> Unit,
    filterOptions: List<String>,
    selectedOptions: MutableMap<String, Boolean>,
    onClickFilterChip: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onSheetDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {
            BottomSheetHead(sheetState, onSheetDismiss)

            FilterChipList(
                filterOptions = filterOptions,
                selectedOptions = selectedOptions,
                onClickFilterChip = onClickFilterChip
            )
        }
    }
}

@Composable
fun BottomSheetHead(
    sheetState: SheetState,
    onSheetDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Filter",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp).weight(1F)
        )

        IconButton(
            onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onSheetDismiss()
                    }
                }
            }
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close Bottom Sheet"
            )
        }
    }
}

@Composable
fun FilterChipList(
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