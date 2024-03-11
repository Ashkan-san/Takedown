package ui.scoreboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import ui.navigation.NavItem
import ui.scoreboard.details.Info
import ui.scoreboard.score.Score
import ui.scoreboard.settings.Settings
import ui.scoreboard.timer.Timer
import ui.scoreboard.timer.noRippleClickable
import ui.util.bottomSheet.CustomBottomSheet

@Composable
fun ScoreboardScreen(
    navigator: Navigator,
    viewModel: ScoreboardViewModel = koinInject()
) {
    val details = remember { viewModel.wrestleDetails }
    val style = remember { viewModel.wrestleStyle }

    val timer = remember { viewModel.timer }

    val redState = remember { viewModel.wrestlerRed }
    val blueState = remember { viewModel.wrestlerBlue }

    // In Kombination mit clickable, um keyboard zu hiden, wenn man außerhalb des timers clickt
    val hideKeyboard = remember { mutableStateOf(false) }

    val showBottomSheet = remember { viewModel.showBottomSheet }

    val mode = remember { viewModel.wrestleMode }
    val isSoundPlaying = remember { viewModel.isSoundPlaying }
    val playSound = remember { viewModel.playSound }
    val scoreHistory = remember { viewModel.scoreHistory }

    ScoreboardScaffold(
        navigator = navigator,
        viewModel = viewModel,
        title = NavItem.Scoreboard.title
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .noRippleClickable { hideKeyboard.value = true },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // STYLE, RUNDE, GEWICHT
            Info(
                style = style.value,
                styles = viewModel.wrestleStyles,
                wrestleDetails = details.value,
                onClickStyle = { style -> viewModel.setWrestleStyle(style = style) },
                onClickPeriod = { period -> viewModel.setPeriod(period = period) },
                onClickWeight = { weight -> viewModel.setDetails(weight = weight) }

            )

            // TIMER
            Timer(
                timer = timer.value,
                timers = viewModel.timers,
                hideKeyboard = hideKeyboard.value,
                isSoundPlaying = isSoundPlaying.value,
                onFocusTimer = { viewModel.pauseTimer() },
                onTimerUpdate = { state -> viewModel.setTimer(state) },
                onClickPlay = {
                    mode.value.function()
                    //viewModel.startTimer()
                },
                onClickStop = { viewModel.stopTimer() },
                onClickReset = { viewModel.resetTimer() },
                onSetTimer = { state ->
                    viewModel.pauseTimer()
                    viewModel.setTimer(state, true)
                },
                onResetKeyboard = { hideKeyboard.value = false },
                onSetPlaySound = { bool -> viewModel.setIsSoundPlaying(bool) }
            )

            // PUNKTE, PENALTY, NAME
            Score(
                red = redState.value,
                blue = blueState.value,
                onAdd = { state -> viewModel.increaseScore(state) },
                onSub = { state -> viewModel.decreaseScore(state) },
                onPenalty = { state -> viewModel.setPenalty(state) },
                onPassive = { state -> viewModel.togglePassive(state) },
                onClickItem = { state, value -> viewModel.increaseScore(state, value) },
            )

            if (showBottomSheet.value) {
                CustomBottomSheet(
                    title = "Scoreboard Settings",
                    onSheetDismiss = { viewModel.toggleBottomSheet(false) }
                ) {
                    Settings(
                        scoreHistory = scoreHistory,
                        wrestleModeSettings = viewModel.wrestleModeSettings,
                        soundSettings = viewModel.soundSettings,
                        resetSettings = viewModel.resetSettings,
                        currentMode = mode.value,
                        playSound = playSound.value,
                        onSetMode = { mode -> viewModel.setMode(mode) }
                    )
                }
            }

        }

    }
}
