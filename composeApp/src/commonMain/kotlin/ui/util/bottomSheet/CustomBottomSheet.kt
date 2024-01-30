package ui.util.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomSheet(
    title: String,
    sheetState: SheetState,
    onSheetDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onSheetDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
        ) {
            BottomSheetHead(
                title = title,
                sheetState = sheetState,
                onSheetDismiss = onSheetDismiss
            )
            Spacer(Modifier.height(20.dp))

            content()
        }
    }
}