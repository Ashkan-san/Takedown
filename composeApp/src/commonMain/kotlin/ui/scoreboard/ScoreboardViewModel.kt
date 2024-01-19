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
import model.scoreboard.WrestlerColor
import model.scoreboard.WrestlerState


class ScoreboardViewModel : KMMViewModel() {
    val wrestlerBlue = mutableStateOf(
        WrestlerState(WrestlerColor.BLUE, Color(0xFF0B61A4), 0, 0)
    )
    val wrestlerRed = mutableStateOf(
        WrestlerState(WrestlerColor.RED, Color(0xFFB72200), 0, 0)
    )

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

    fun resetAllScores() {
        wrestlerBlue.value = wrestlerBlue.value.copy(score = 0, penalty = 0)
        wrestlerRed.value = wrestlerRed.value.copy(score = 0, penalty = 0)
    }

    /*fun updateScoreState(
        blue: Int = scoreState.value.scoreBlue,
        red: Int = scoreState.value.scoreRed
    ) {
        scoreState.value = scoreState.value.copy(scoreBlue = blue, scoreRed = red)
    }*/

    val round = mutableStateOf(1)

    fun increaseRound() {
        round.value += 1
    }

    private var timerJob: Job? = null

    val defaultMinutes = 3
    val defaultSeconds = 0
    val startValue = defaultMinutes * 60 + defaultSeconds

    var timerState = mutableStateOf(setTimer(startValue))

    var isRunning = mutableStateOf(false)

    fun startTimer() {
        if (timerJob?.isActive != true) {
            startTimerJob()
        } else {
            pauseTimer()
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        isRunning.value = false
    }

    fun stopTimer() {
        resetTimer()
        increaseRound()
    }

    fun resetTimer() {
        timerJob?.cancel()
        isRunning.value = false
        timerState.value = setTimer(startValue)
    }

    fun setTimerState(
        minutes: String = timerState.value.minutes,
        seconds: String = timerState.value.seconds
    ) {
        timerState.value = timerState.value.copy(minutes = minutes, seconds = seconds)
    }

    private fun startTimerJob() {
        isRunning.value = true

        var timerValue = timerState.value.minutes.toInt() * 60 + timerState.value.seconds.toInt()

        timerJob = viewModelScope.coroutineScope.launch(Dispatchers.Main) {
            for (i in timerValue downTo 0) {
                timerValue = i
                timerState.value = setTimer(i)

                // Wait for one second
                delay(1000)

                if (i == 0) {
                    stopTimer()
                }
            }
        }
    }

    private fun setTimer(value: Int): TimerState {
        val minutes = if (value / 60 < 10) "0${value / 60}" else "${value / 60}"
        val seconds = if (value % 60 < 10) "0${value % 60}" else "${value % 60}"

        return TimerState(minutes, seconds)
    }

}