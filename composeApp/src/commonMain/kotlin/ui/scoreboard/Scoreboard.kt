package ui.scoreboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.scoreboard.punkte.PunkteSurface
import ui.scoreboard.timer.Timer

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
    val scoreRed = remember { viewModel.scoreRed }

    val timerState = remember { viewModel.timerState }
    val runningState = remember { viewModel.isRunning }

    ScoreboardScaffold(
        navigator = navigator,
        title = Screen.Scoreboard.title
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // RUNDE
            Runde()

            // TIMER
            Timer(
                timerState = timerState.value,
                runningState = runningState.value,
                onSelectTimer = { viewModel.pauseTimer() },
                onChangeTimerMin = { value -> viewModel.setTimerState(minutes = value) },
                onChangeTimerSec = { value -> viewModel.setTimerState(seconds = value) },
                onClickPlay = { viewModel.startTimer() },
                onClickStop = { viewModel.stopTimer() },
                onClickReset = { viewModel.resetTimer() }
            )

            // PUNKTE
            Row(
                modifier = Modifier
            ) {
                PunkteSurface(
                    modifier = Modifier.weight(1F),
                    //color = MaterialTheme.colorScheme.primary,
                    color = Color(0xFF0B61A4),
                    score = scoreBlue.value,
                    onClick = { viewModel.increaseBlue() }
                )
                PunkteSurface(
                    modifier = Modifier.weight(1F),
                    //color = MaterialTheme.colorScheme.secondary,
                    color = Color(0xFFB72200),
                    score = scoreRed.value,
                    onClick = { viewModel.increaseRed() }
                )
            }

        }

    }
}
