package ui.scoreboard

import androidx.compose.runtime.mutableStateOf
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.scoreboard.TimerState

class ScoreboardViewModel : KMMViewModel() {
    val scoreBlue = mutableStateOf(0)
    val scoreRed = mutableStateOf(0)

    fun increaseBlue() {
        scoreBlue.value += 1
    }

    fun increaseRed() {
        scoreRed.value += 1
    }

    fun resetPoints() {
        scoreBlue.value = 0
        scoreRed.value = 0
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
        timerJob?.cancel()
        isRunning.value = false
        timerState.value = setTimer(startValue)
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
                    resetTimer()
                }
            }
        }
    }

    fun setTimer(value: Int): TimerState {
        val minutes = if (value / 60 < 10) "0${value / 60}" else "${value / 60}"
        val seconds = if (value % 60 < 10) "0${value % 60}" else "${value % 60}"

        return TimerState(minutes, seconds)
    }

}