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
import ui.scoreboard.info.Info
import ui.scoreboard.score.Score
import ui.scoreboard.settings.Settings
import ui.scoreboard.timer.Timer
import ui.scoreboard.timer.noRippleClickable
import ui.util.bottomSheet.CustomBottomSheet

@Composable
fun ScoreboardScreen(navigator: Navigator, viewModel: ScoreboardViewModel) {
    val infoState = remember { viewModel.wrestleDetailsState }
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

    val modeState = remember { viewModel.currentWrestleMode }
    val wrestleModeSettings = remember { viewModel.wrestleModeSettings }
    val resetSettings = remember { viewModel.resetSettings }
    val soundSettings = remember { viewModel.soundSettings }
    val isSoundPlaying = remember { viewModel.isSoundPlaying }
    val playSound = remember { viewModel.playSound }

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
                wrestleDetailsState = infoState.value,
                onClickStyle = { style -> viewModel.setWrestleStyle(style) },
                onClickRound = { round -> viewModel.setRound(round) },
                onClickWeight = { weight -> viewModel.setWeight(weight) }

            )

            // TIMER
            Timer(
                timerState = timerState.value,
                timerList = timerList,
                hideKeyboard = hideKeyboard.value,
                isSoundPlaying = isSoundPlaying.value,
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
                    viewModel.setTimerState(state, true)
                },
                onResetKeyboard = { hideKeyboard.value = false },
                onSetPlaySound = { bool -> viewModel.setIsSoundPlaying(bool) }
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
                    Settings(
                        wrestleModeSettings = wrestleModeSettings,
                        resetSettings = resetSettings,
                        soundSettings = soundSettings,
                        modeState = modeState.value,
                        playSound = playSound.value,
                        onSetWrestleMode = { mode -> viewModel.setWrestleModeSetting(mode) }
                    )
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

