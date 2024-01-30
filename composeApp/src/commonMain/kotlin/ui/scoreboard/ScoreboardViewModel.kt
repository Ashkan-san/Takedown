package ui.scoreboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExposureZero
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.scoreboard.SettingState
import model.scoreboard.TimerState
import model.scoreboard.WrestleDetailsState
import model.scoreboard.WrestleStyle
import model.scoreboard.WrestlerColor
import model.scoreboard.WrestlerState


class ScoreboardViewModel : KMMViewModel() {
    // SETTINGS
    val showBottomSheet = mutableStateOf(false)

    val wrestleModeSettings = listOf(
        SettingState("Classic Mode", "Timer doesn't start automatically", null, null, null, ::classicMode),
        SettingState("Tournament Mode", "Timer starts automatically", null, null, null, ::tournamentMode),
        SettingState("Infinite Mode", "Timer repeats indefinitely", null, null, null, ::infiniteMode),
    )
    val currentWrestleMode = mutableStateOf(wrestleModeSettings[0])

    val resetSettings = listOf(
        SettingState("Reset infos", "Reset wrestle style, round and weight class", null, Icons.Default.Info, "Reset Info Icon", ::resetInfos),
        SettingState("Reset timer", "Reset timer", null, Icons.Default.Timer, "Reset Timer Icon", ::resetTimer),
        SettingState("Reset scores", "Reset scores, penalties and passivity", null, Icons.Default.ExposureZero, "Reset Scores Icon", ::resetScores),
        SettingState("Reset all", "Reset all", null, Icons.Default.Refresh, "Reset All Icon", ::resetAll)
    )

    val soundSettings = listOf(
        SettingState("Sound Effect", "Sound when the round ends or whistle is clicked", null, Icons.Default.Sports, "Play Sound Icon", ::setSoundPlay)
    )

    val isSoundPlaying = mutableStateOf(false)
    val playSound = mutableStateOf(true)

    val wrestlerRed = mutableStateOf(WrestlerState(WrestlerColor.RED, Color(0xFFB72200), 0, 0))
    val wrestlerBlue = mutableStateOf(WrestlerState(WrestlerColor.BLUE, Color(0xFF0B61A4), 0, 0))

    val wrestleStyles = listOf(
        WrestleStyle(
            "Men's Freestyle",
            "MFS",
            10,
            listOf(57, 65, 74, 86, 97, 125)
        ),
        WrestleStyle(
            "Women's Freestyle",
            "WFS",
            10,
            listOf(50, 53, 57, 62, 68, 76)
        ),
        WrestleStyle(
            "Greco-Roman",
            "GR",
            8,
            listOf(60, 67, 77, 87, 97, 130)
        )
    )

    val wrestleStyle = mutableStateOf(wrestleStyles[0])
    val roundList = (1..10).toList()
    val round = mutableStateOf(1)

    val wrestleDetailsState =
        mutableStateOf(WrestleDetailsState(wrestleStyle.value.abbreviation, "Round ${round.value}", "${wrestleStyle.value.weightClasses[0]} kg"))

    // TIMER
    private var timerJob: Job? = null
    val timerList = listOf(
        TimerState("00", "30"),
        TimerState("01", "00"),
        TimerState("01", "30"),
        TimerState("02", "00"),
        TimerState("02", "30"),
        TimerState("03", "00"),
    )
    private val defaultTimerState = mutableStateOf(TimerState())
    val timerState = mutableStateOf(defaultTimerState.value)

    fun toggleBottomSheet(boolean: Boolean) {
        showBottomSheet.value = boolean
    }

    fun setIsSoundPlaying(boolean: Boolean) {
        if (playSound.value) isSoundPlaying.value = boolean
    }

    fun setSoundPlay() {
        playSound.value = !playSound.value
    }

    fun classicMode() {

    }

    fun tournamentMode() {

    }

    fun infiniteMode() {

    }

    fun setWrestleModeSetting(mode: SettingState) {
        currentWrestleMode.value = mode
    }

    fun setWrestleStyle(style: WrestleStyle) {
        wrestleStyle.value = style
        setInfoState(style = style.abbreviation, weight = "${style.weightClasses[0]} kg")
    }

    fun setRound(value: Int = 1, increment: Boolean = false) {
        if (increment) round.value += value else round.value = value
        setInfoState(round = "Round ${round.value}")
    }

    fun setWeight(weight: Int) {
        setInfoState(weight = "$weight kg")
    }

    fun setInfoState(
        style: String = wrestleDetailsState.value.style,
        round: String = wrestleDetailsState.value.round,
        weight: String = wrestleDetailsState.value.weight
    ) {
        wrestleDetailsState.value = wrestleDetailsState.value.copy(style = style, round = round, weight = weight)
    }

    fun resetInfos() {
        // TODO ÄNDERN ALLES, SEHR DRECKIG AKTUELL
        setWrestleStyle(wrestleStyles[0])
        setRound()
    }

    fun increaseScore(state: WrestlerState, value: Int = 1) {
        when (state.colorName) {
            WrestlerColor.RED -> {
                wrestlerRed.value = state.copy(score = state.score + value)
            }

            WrestlerColor.BLUE -> {
                wrestlerBlue.value = state.copy(score = state.score + value)
            }
        }
    }

    fun decreaseScore(state: WrestlerState) {
        if (state.score != 0) {
            when (state.colorName) {
                WrestlerColor.RED -> {
                    wrestlerRed.value = state.copy(score = state.score - 1)
                }

                WrestlerColor.BLUE -> {
                    wrestlerBlue.value = state.copy(score = state.score - 1)
                }
            }
        }
    }

    fun setPenalty(state: WrestlerState) {
        when (state.colorName) {
            WrestlerColor.RED -> {
                wrestlerRed.value = state.copy(penalty = state.penalty + 1)
                if (wrestlerRed.value.penalty > 3) {
                    wrestlerRed.value = state.copy(penalty = 0)
                }
            }

            WrestlerColor.BLUE -> {
                wrestlerBlue.value = state.copy(penalty = state.penalty + 1)
                if (wrestlerBlue.value.penalty > 3) {
                    wrestlerBlue.value = state.copy(penalty = 0)
                }
            }
        }
    }

    fun togglePassive(state: WrestlerState) {
        when (state.colorName) {
            WrestlerColor.RED -> {
                wrestlerRed.value = state.copy(passive = !wrestlerRed.value.passive)
            }

            WrestlerColor.BLUE -> {
                wrestlerBlue.value = state.copy(passive = !wrestlerBlue.value.passive)
            }
        }
    }

    fun resetScores() {
        wrestlerRed.value = wrestlerRed.value.copy(score = 0, penalty = 0, passive = false)
        wrestlerBlue.value = wrestlerBlue.value.copy(score = 0, penalty = 0, passive = false)
    }

    fun resetAll() {
        // Wrestle Style, Round, Weight
        resetInfos()

        // Timer
        resetTimer()

        // Score, Penalty
        resetScores()
    }

    fun wrestleMode() {
        // ÄNDERN
        /*viewModelScope.coroutineScope.launch(Dispatchers.Main) {
            setTimerState("00", "05")
            startTimerJob()
            delay(5000)
            setTimerState("00", "10")
            startTimerJob()
            delay(5000)
        }*/
    }

    fun startTimer() {
        if (timerJob?.isActive != true) {
            startTimerJob()
        } else {
            pauseTimer()
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerState.value = timerState.value.copy(isRunning = false)
    }

    fun stopTimer() {
        // Nur Runde erhöhen, wenn der Timer läuft
        // Nur Timer resetten, wenn nicht wrestle mode
        if (timerState.value.isRunning) {
            setIsSoundPlaying(true)
            setRound(increment = true)
        }
        resetTimer()
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerState.value = defaultTimerState.value
    }

    fun setTimerState(state: TimerState, setDefault: Boolean = false) {
        timerState.value = state

        if (setDefault) defaultTimerState.value = state
    }

    private fun startTimerJob() {
        timerState.value = timerState.value.copy(isRunning = true)
        val timerValue = timerState.value.minutes.toInt() * 60 + timerState.value.seconds.toInt()

        timerJob = viewModelScope.coroutineScope.launch(Dispatchers.Main) {
            for (i in timerValue downTo 0) {
                timerState.value = setFormatTimer(timerState.value, i)

                delay(1000)

                if (i == 0) {
                    stopTimer()
                }
            }
        }
    }

    private fun setFormatTimer(timerState: TimerState, value: Int): TimerState {
        val minutes = if (value / 60 < 10) "0${value / 60}" else "${value / 60}"
        val seconds = if (value % 60 < 10) "0${value % 60}" else "${value % 60}"

        return timerState.copy(minutes = minutes, seconds = seconds)
    }

}