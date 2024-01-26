package ui.scoreboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.Navigator
import ui.navigation.Screen
import ui.scoreboard.info.Info
import ui.scoreboard.score.Score
import ui.scoreboard.settings.SelectorSetting
import ui.scoreboard.settings.SettingsBox
import ui.scoreboard.timer.Timer
import ui.scoreboard.timer.noRippleClickable
import ui.util.bottomSheet.CustomBottomSheet

@Composable
fun ScoreboardScreen(navigator: Navigator, viewModel: ScoreboardViewModel) {
    val infoState = remember { viewModel.infoState }
    val styleState = remember { viewModel.wrestleStyle }
    val styleList = remember { viewModel.wrestleStyles }
    val roundList = remember { viewModel.roundList }

    val timerState = remember { viewModel.timerState }
    val timerList = remember { viewModel.timerList }

    val redState = remember { viewModel.wrestlerRed }
    val blueState = remember { viewModel.wrestlerBlue }

    // In Kombination mit clickable, um keyboard zu hiden, wenn man außerhalb des timers clickt
    val hideKeyboard = remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { viewModel.showBottomSheet }
    val modeState = remember { viewModel.currentMode }
    val wrestleModes = remember { viewModel.wrestleModes }
    val settings = remember { viewModel.settings }

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
            Info(
                style = styleState.value,
                styleList = styleList,
                roundList = roundList,
                infoState = infoState.value,
                onClickStyle = { style -> viewModel.setWrestleStyle(style) },
                onClickRound = { round -> viewModel.setRound(round) },
                onClickWeight = { weight -> viewModel.setWeight(weight) }

            )

            // TIMER
            Timer(
                timerState = timerState.value,
                timerList = timerList,
                hideKeyboard = hideKeyboard.value,
                onFocusTimer = { viewModel.pauseTimer() },
                onTimerUpdate = { state -> viewModel.setTimerState(state) },
                onClickPlay = {
                    viewModel.startTimer()
                    //if (wrestleModeState.value) viewModel.wrestleMode() else viewModel.startTimer()
                },
                onClickStop = { viewModel.stopTimer() },
                onClickReset = { viewModel.resetTimer() },
                onSetTimer = { state ->
                    viewModel.pauseTimer()
                    viewModel.setDefaultTimerState(state)
                },
                onResetKeyboard = { hideKeyboard.value = false }
            )

            // PUNKTE, PENALTY, NAME
            Score(
                redState = redState.value,
                blueState = blueState.value,
                onAdd = { state -> viewModel.increaseScore(state) },
                onSub = { state -> viewModel.decreaseScore(state) },
                onPenalty = { state -> viewModel.setPenalty(state) },
                onPassive = { state -> viewModel.togglePassive(state) },
                onClickItem = { state, value -> viewModel.increaseScore(state, value) },
            )

            // TODO bug fixen mit navbar, die ist transparent wenn sheet expanded ist
            if (showBottomSheet.value) {
                CustomBottomSheet(
                    title = "Scoreboard Settings",
                    sheetState = sheetState,
                    onSheetDismiss = { viewModel.toggleBottomSheet(false) }
                ) {
                    // Wrestling Style

                    // Wrestle Mode
                    SettingsBox(
                        title = "Wrestle Mode"
                    ) {
                        wrestleModes.forEach { mode ->
                            SelectorSetting(
                                title = mode.title,
                                description = mode.description,
                                leading = {
                                    RadioButton(
                                        selected = (mode == modeState.value),
                                        onClick = { viewModel.setWrestleMode(mode) }
                                    )
                                },
                                onClick = { viewModel.setWrestleMode(mode) }
                            )
                        }
                    }

                    // Appearance

                    // Reset
                    SettingsBox(
                        title = "Reset"
                    ) {
                        settings.forEach { setting ->
                            SelectorSetting(
                                title = setting.title,
                                description = setting.description,
                                leading = {
                                    Icon(setting.icon, setting.iconDescription)
                                },
                                onClick = {
                                    setting.function()
                                }
                            )
                        }
                    }
                }
            }

        }

    }
}

//SettingIcon(Icons.Default.SportsKabaddi)
/*Switch(
    modifier = Modifier.scale(0.8f),
    checked = modeState.value,
    onCheckedChange = {
        viewModel.setWrestleMode()
    }
)*/

