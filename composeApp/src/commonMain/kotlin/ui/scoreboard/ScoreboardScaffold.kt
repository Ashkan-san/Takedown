package ui.scoreboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ScoreboardScaffold(
    navigator: Navigator,
    viewModel: ScoreboardViewModel,
    title: String,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                        )
                    }
                },
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleBottomSheet(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "More Options Icon",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}