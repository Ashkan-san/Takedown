package ui.scoreboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.scoreboard.punkte.Punkte
import ui.scoreboard.runde.Runde
import ui.scoreboard.timer.Timer
import ui.scoreboard.timer.noRippleClickable
import ui.util.bottomSheet.CustomBottomSheet

@Composable
fun ScoreboardScreen(navigator: Navigator, viewModel: ScoreboardViewModel) {
    val blueState = remember { viewModel.wrestlerBlue }
    val redState = remember { viewModel.wrestlerRed }
    val roundState = remember { viewModel.round }
    val timerState = remember { viewModel.timerState }

    // In Kombination mit clickable, um keyboard zu hiden, wenn man außerhalb des timers clickt
    val hideKeyboard = remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { viewModel.showBottomSheet }

    ScoreboardScaffold(
        navigator = navigator,
        viewModel = viewModel,
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
                blueState = blueState.value,
                redState = redState.value,
                onAdd = { state -> viewModel.increaseScore(state) },
                onSub = { state -> viewModel.decreaseScore(state) },
                onPenalty = { state -> viewModel.setPenalty(state) },
                onClickItem = { state, value -> viewModel.increaseScore(state, value) },
            )

            if (showBottomSheet.value) {
                CustomBottomSheet(
                    title = "Scoreboard Settings",
                    sheetState = sheetState,
                    onSheetDismiss = { viewModel.toggleBottomSheet(false) }
                ) {
                    // Style, Appearance, Fight Mode, Resets

                }
            }

        }

    }
}
