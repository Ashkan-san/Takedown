package ui.scoreboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import commons.navigation.NavItem
import commons.ui.scaffold.BackAndSettingTopBar
import ui.scoreboard.timer.noRippleClickable

@Composable
fun ScoreboardScreen(
    viewModel: ScoreboardViewModel = koinInject(),
    onBack: () -> Unit = {},
) {
    var hideKeyboard by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BackAndSettingTopBar(
                title = NavItem.Scoreboard.title,
                onClickBack = { onBack() },
                onClickSettings = { showBottomSheet = true }
            )
        },
    ) { innerPadding ->
        ScoreboardContent(
            viewModel = viewModel,
            hideKeyboard = hideKeyboard,
            showBottomSheet = showBottomSheet,
            onResetKeyboard = { hideKeyboard = true },
            onSheetDismiss = { showBottomSheet = false },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .noRippleClickable { hideKeyboard = true }
        )
    }
}
