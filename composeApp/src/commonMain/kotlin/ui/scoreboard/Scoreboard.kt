package ui.scoreboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import model.scoreboard.WrestlerColor
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.scoreboard.punkte.Punkte
import ui.scoreboard.runde.Runde
import ui.scoreboard.timer.Timer
import ui.scoreboard.timer.noRippleClickable

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
    val scoreState = remember { viewModel.scoreState }

    val roundState = remember { viewModel.round }

    val timerState = remember { viewModel.timerState }
    val runningState = remember { viewModel.isRunning }

    // In Kombination mit clickable, um keyboard zu hiden, wenn man außerhalb des timers clickt
    val hideKeyboard = remember { mutableStateOf(false) }

    ScoreboardScaffold(
        navigator = navigator,
        title = Screen.Scoreboard.title
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .noRippleClickable { hideKeyboard.value = true },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // RUNDE
            Runde(roundState.value)

            // TIMER
            Timer(
                timerState = timerState.value,
                runningState = runningState.value,
                hideKeyboard = hideKeyboard.value,
                onFocusTimer = { viewModel.pauseTimer() },
                onChangeTimerMin = { value -> viewModel.setTimerState(minutes = value) },
                onChangeTimerSec = { value -> viewModel.setTimerState(seconds = value) },
                onClickPlay = { viewModel.startTimer() },
                onClickStop = { viewModel.stopTimer() },
                onClickReset = { viewModel.resetTimer() },
                onSetTimer = { timer ->
                    viewModel.pauseTimer()
                    viewModel.setTimerState(minutes = timer.minutes, seconds = timer.seconds)
                },
                onResetKeyboard = { hideKeyboard.value = false }
            )

            // PUNKTE, PENALTY, NAME
            Punkte(
                scoreState = scoreState.value,
                onAddBlue = { viewModel.increaseScore(WrestlerColor.BLUE) },
                onAddRed = { viewModel.increaseScore(WrestlerColor.RED) },
                onSubBlue = { viewModel.decreaseScore(WrestlerColor.BLUE) },
                onSubRed = { viewModel.decreaseScore(WrestlerColor.RED) },
                onPenaltyBlue = { viewModel.setPenalty(WrestlerColor.BLUE) },
                onPenaltyRed = { viewModel.setPenalty(WrestlerColor.RED) },
                onClickItemBlue = { value -> viewModel.increaseScore(WrestlerColor.BLUE, value) },
                onClickItemRed = { value -> viewModel.increaseScore(WrestlerColor.RED, value) },
            )

        }

    }
}
