package ui.scoreboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen

@Composable
fun ScoreboardScaffold(
    navigator: Navigator,
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
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        // TODO dropdown mit optionen
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options Icon",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun ScoreboardScreen(navigator: Navigator, viewModel: ScoreboardViewModel) {
    val scoreBlue = remember { viewModel.scoreBlue }
    val scoreRed = viewModel.scoreRed

    ScoreboardScaffold(
        navigator = navigator,
        title = Screen.Scoreboard.title
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.clickable { viewModel.increaseBlue() },
                    text = "${scoreBlue.value}"
                )
                IconButton(
                    onClick = { viewModel.increaseBlue() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Icon"
                    )
                }
            }
        }

    }
}