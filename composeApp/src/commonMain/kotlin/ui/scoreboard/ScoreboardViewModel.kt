package ui.scoreboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.scoreboard.InfoState
import model.scoreboard.TimerState
import model.scoreboard.WrestleMode
import model.scoreboard.WrestleModeType
import model.scoreboard.WrestleStyle
import model.scoreboard.WrestlerColor
import model.scoreboard.WrestlerState


class ScoreboardViewModel : KMMViewModel() {
    val showBottomSheet = mutableStateOf(false)
    val wrestleModes = listOf(
        WrestleMode("Classic Mode", "Timer doesn't start automatically", WrestleModeType.CLASSIC),
        WrestleMode("Tournament Mode", "Timer starts automatically", WrestleModeType.TOURNAMENT),
        WrestleMode("Infinite Mode", "Timer repeats indefinitely", WrestleModeType.INFINITE)
    )
    val currentMode = mutableStateOf(wrestleModes[0])

    val wrestlerRed = mutableStateOf(
        WrestlerState(WrestlerColor.RED, Color(0xFFB72200), 0, 0)
    )
    val wrestlerBlue = mutableStateOf(
        WrestlerState(WrestlerColor.BLUE, Color(0xFF0B61A4), 0, 0)
    )

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
    val currentRound = mutableStateOf(1)
    val infoState =
        mutableStateOf(InfoState(wrestleStyle.value.abbreviation, "Round ${currentRound.value}", "${wrestleStyle.value.weightClasses[0]} kg"))

    fun setWrestleStyle(style: WrestleStyle) {
        wrestleStyle.value = style
    }

    fun setInfoState(
        style: String = infoState.value.style,
        round: String = infoState.value.round,
        weight: String = infoState.value.weight
    ) {
        infoState.value = infoState.value.copy(style = style, round = round, weight = weight)
    }

    fun roundsToStrings(roundList: List<Int>): List<String> {
        return roundList.map { round ->
            "Round $round"
        }
    }

    fun weightsToStrings(weightList: List<Int>): List<String> {
        return weightList.map { weight ->
            "${weight}kg"
        }
    }

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

    fun setWrestleMode(mode: WrestleMode) {
        currentMode.value = mode
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

    fun resetAllScores() {
        wrestlerRed.value = wrestlerRed.value.copy(score = 0, penalty = 0)
        wrestlerBlue.value = wrestlerBlue.value.copy(score = 0, penalty = 0)
    }

    fun increaseRound() {
        currentRound.value += 1
        infoState.value = infoState.value.copy(round = "Round ${currentRound.value}")
    }

    fun setRound(value: Int) {
        currentRound.value = value
        infoState.value = infoState.value.copy(round = "Round ${currentRound.value}")
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
        if (timerState.value.isRunning) increaseRound()
        resetTimer()
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerState.value = defaultTimerState.value
    }

    fun setDefaultTimerState(state: TimerState) {
        timerState.value = state
        defaultTimerState.value = state
    }

    fun setTimerState(state: TimerState) {
        timerState.value = state
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