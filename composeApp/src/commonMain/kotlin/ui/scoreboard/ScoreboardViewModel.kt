package ui.scoreboard

import androidx.compose.runtime.mutableStateOf
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.scoreboard.PunkteState
import model.scoreboard.TimerState
import model.scoreboard.WrestlerColor


class ScoreboardViewModel : KMMViewModel() {
    val scoreState = mutableStateOf(PunkteState(0, 0, 0, 0))

    fun setPenalty(color: WrestlerColor) {
        when (color) {
            WrestlerColor.BLUE -> {
                scoreState.value = scoreState.value.copy(penaltyBlue = scoreState.value.penaltyBlue + 1)
                if (scoreState.value.penaltyBlue > 3) {
                    scoreState.value = scoreState.value.copy(penaltyBlue = 0)
                }
            }

            WrestlerColor.RED -> {
                scoreState.value = scoreState.value.copy(penaltyRed = scoreState.value.penaltyRed + 1)
                if (scoreState.value.penaltyRed > 3) {
                    scoreState.value = scoreState.value.copy(penaltyRed = 0)
                }
            }
        }
    }

    fun increaseScore(color: WrestlerColor, value: Int = 1) {
        when (color) {
            WrestlerColor.BLUE -> {
                scoreState.value = scoreState.value.copy(scoreBlue = scoreState.value.scoreBlue + value)
            }

            WrestlerColor.RED -> {
                scoreState.value = scoreState.value.copy(scoreRed = scoreState.value.scoreRed + value)
            }
        }
    }

    fun decreaseScore(color: WrestlerColor) {
        when (color) {
            WrestlerColor.BLUE -> {
                if (scoreState.value.scoreBlue != 0) {
                    scoreState.value = scoreState.value.copy(scoreBlue = scoreState.value.scoreBlue - 1)
                }
            }

            WrestlerColor.RED -> {
                if (scoreState.value.scoreRed != 0) {
                    scoreState.value = scoreState.value.copy(scoreRed = scoreState.value.scoreRed - 1)
                }
            }
        }
    }

    fun resetAllScores() {
        scoreState.value = scoreState.value.copy(0, 0, 0, 0)
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