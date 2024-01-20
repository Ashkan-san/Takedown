package ui.scoreboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.scoreboard.TimerState
import model.scoreboard.WrestleMode
import model.scoreboard.WrestleModeType
import model.scoreboard.WrestlerColor
import model.scoreboard.WrestlerState


class ScoreboardViewModel : KMMViewModel() {
    val showBottomSheet = mutableStateOf(true)
    val wrestleModes = listOf(
        WrestleMode("Classic Mode", "Timer doesn't start automatically", WrestleModeType.CLASSIC),
        WrestleMode("Tournament Mode", "Timer starts automatically", WrestleModeType.TOURNAMENT),
        WrestleMode("Infinite Mode", "Timer repeats indefinitely", WrestleModeType.INFINITE)
    )
    val currentMode = mutableStateOf(wrestleModes[0])

    val wrestlerBlue = mutableStateOf(
        WrestlerState(WrestlerColor.BLUE, Color(0xFF0B61A4), 0, 0)
    )
    val wrestlerRed = mutableStateOf(
        WrestlerState(WrestlerColor.RED, Color(0xFFB72200), 0, 0)
    )

    val round = mutableStateOf(1)

    private var timerJob: Job? = null
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
            WrestlerColor.BLUE -> {
                wrestlerBlue.value = state.copy(score = state.score + value)
            }

            WrestlerColor.RED -> {
                wrestlerRed.value = state.copy(score = state.score + value)
            }
        }
    }

    fun decreaseScore(state: WrestlerState) {
        if (state.score != 0) {
            when (state.colorName) {
                WrestlerColor.BLUE -> {
                    wrestlerBlue.value = state.copy(score = state.score - 1)
                }

                WrestlerColor.RED -> {
                    wrestlerRed.value = state.copy(score = state.score - 1)
                }
            }
        }
    }

    fun setPenalty(state: WrestlerState) {
        when (state.colorName) {
            WrestlerColor.BLUE -> {
                wrestlerBlue.value = state.copy(penalty = state.penalty + 1)
                if (wrestlerBlue.value.penalty > 3) {
                    wrestlerBlue.value = state.copy(penalty = 0)
                }
            }

            WrestlerColor.RED -> {
                wrestlerRed.value = state.copy(penalty = state.penalty + 1)
                if (wrestlerRed.value.penalty > 3) {
                    wrestlerRed.value = state.copy(penalty = 0)
                }
            }
        }
    }

    fun resetAllScores() {
        wrestlerBlue.value = wrestlerBlue.value.copy(score = 0, penalty = 0)
        wrestlerRed.value = wrestlerRed.value.copy(score = 0, penalty = 0)
    }

    fun increaseRound() {
        round.value += 1
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
        //if (wrestleMode.value && !timerState.value.isRunning) wrestleMode()
        if (timerJob?.isActive != true) {
            println("start")
            startTimerJob()
        } else {
            println("stop")
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
        //if (!wrestleMode.value) resetTimer()
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
                //timerValue = i
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