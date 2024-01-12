package ui.scoreboard

import androidx.compose.runtime.mutableStateOf
import com.rickclephas.kmm.viewmodel.KMMViewModel

class ScoreboardViewModel : KMMViewModel() {
    val scoreBlue = mutableStateOf(0)
    val scoreRed = mutableStateOf(0)

    fun increaseBlue() {
        scoreBlue.value += 1
    }

    fun increaseRed() {
        scoreRed.value.plus(1)
    }

    fun resetPoints() {
        scoreBlue.value = 0
        scoreRed.value = 0
    }
}