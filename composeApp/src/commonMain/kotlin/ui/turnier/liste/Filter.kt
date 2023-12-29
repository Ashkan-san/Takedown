package ui.turnier.liste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun SearchFilterRow(
    onSearchChanged: (String) -> Unit
) {
    // Ob der Sheet hidden, expanded ist usw.
    val sheetState = rememberModalBottomSheetState()
    // Ob der Sheet in der Composition zu sehen ist
    val showBottomSheet = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier.weight(1F),
            searchDisplay = "",
            onSearchChanged = {
                onSearchChanged(it)
            },
            onSearchClosed = {
                //searchQuery.value = ""
            }
        )

        // FILTER
        IconButton(
            onClick = { showBottomSheet.value = true }
        ) {
            Icon(
                Icons.Default.FilterList,
                contentDescription = "Filter"
            )
        }
    }

    if (showBottomSheet.value) {
        BasicBottomSheet(
            sheetState = sheetState,
            onSheetDismiss = {
                showBottomSheet.value = false
            }
        )
    }
}

@Composable
fun BasicBottomSheet(
    sheetState: SheetState,
    onSheetDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            onSheetDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {
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

            FilterChips()
        }
    }
}

@Composable
fun FilterChips() {
    // Filter
    val selected = remember { mutableStateOf(false) }

    FilterChip(
        selected = selected.value,
        onClick = { selected.value = !selected.value },
        label = { Text("Kommende Turniere") }
    )
}