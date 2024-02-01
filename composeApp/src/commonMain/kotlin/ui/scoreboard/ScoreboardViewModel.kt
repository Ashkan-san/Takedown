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
import model.scoreboard.SettingType
import model.scoreboard.TimerState
import model.scoreboard.WrestleDetailsState
import model.scoreboard.WrestleStyle
import model.scoreboard.WrestlerColor
import model.scoreboard.WrestlerState


class ScoreboardViewModel : KMMViewModel() {
    // SETTINGS
    val showBottomSheet = mutableStateOf(false)

    val wrestleModeSettings = listOf(
        SettingState(SettingType.MODE, "Classic Mode", "Timer doesn't start automatically", null, null, null, ::classicMode),
        SettingState(SettingType.MODE, "Tournament Mode", "Timer starts automatically", null, null, null, ::tournamentMode),
        SettingState(SettingType.MODE, "Infinite Mode", "Timer repeats indefinitely", null, null, null, ::infiniteMode),
    )
    val wrestleMode = mutableStateOf(wrestleModeSettings[0])

    val soundSettings = listOf(
        SettingState(
            SettingType.SOUND,
            "Sound Effect",
            "Sound when the round ends or whistle is clicked",
            null,
            Icons.Default.Sports,
            "Play Sound Icon",
            ::setSoundPlay
        ),
    )

    val isSoundPlaying = mutableStateOf(false)
    val playSound = mutableStateOf(true)

    val resetSettings = listOf(
        SettingState(
            SettingType.RESET,
            "Reset infos",
            "Reset wrestle style, period and weight class",
            null,
            Icons.Default.Info,
            "Reset Info Icon",
            ::resetInfos
        ),
        SettingState(SettingType.RESET, "Reset timer", "Reset timer", null, Icons.Default.Timer, "Reset Timer Icon", ::resetTimer),
        SettingState(
            SettingType.RESET,
            "Reset scores",
            "Reset scores, penalties and passivity",
            null,
            Icons.Default.ExposureZero,
            "Reset Scores Icon",
            ::resetScores
        ),
        SettingState(SettingType.RESET, "Reset all", "Reset all", null, Icons.Default.Refresh, "Reset All Icon", ::resetAll)
    )

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
    val periods = (1..10).toList()
    val period = mutableStateOf(periods[0])

    val wrestleDetails =
        mutableStateOf(WrestleDetailsState(wrestleStyle.value.abbreviation, "Period ${period.value}", "${wrestleStyle.value.weightClasses[0]} kg"))

    // TIMER
    private var timerJob: Job? = null
    val timers = listOf(
        TimerState("00", "30"),
        TimerState("01", "00"),
        TimerState("01", "30"),
        TimerState("02", "00"),
        TimerState("02", "30"),
        TimerState("03", "00"),
    )
    private val defaultTimer = mutableStateOf(TimerState())
    val timer = mutableStateOf(defaultTimer.value)

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

    fun setMode(mode: SettingState) {
        wrestleMode.value = mode
    }

    fun setWrestleStyle(style: WrestleStyle) {
        wrestleStyle.value = style
        setInfoState(style = style.abbreviation, weight = "${style.weightClasses[0]} kg")

        checkWinner()
    }

    fun setPeriod(value: Int = 1, increment: Boolean = false) {
        if (increment) period.value += value else period.value = value
        setInfoState(period = "Period ${period.value}")
    }

    fun setWeight(weight: Int) {
        setInfoState(weight = "$weight kg")
    }

    fun setInfoState(
        style: String = wrestleDetails.value.style,
        period: String = wrestleDetails.value.period,
        weight: String = wrestleDetails.value.weight
    ) {
        wrestleDetails.value = wrestleDetails.value.copy(style = style, period = period, weight = weight)
    }

    fun resetInfos() {
        // TODO ÄNDERN ALLES, SEHR DRECKIG AKTUELL
        setWrestleStyle(wrestleStyles[0])
        setPeriod()
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

        checkWinner()
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

        checkWinner()
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
                wrestlerRed.value = state.copy(isPassive = !wrestlerRed.value.isPassive)
            }

            WrestlerColor.BLUE -> {
                wrestlerBlue.value = state.copy(isPassive = !wrestlerBlue.value.isPassive)
            }
        }
    }

    fun checkWinner() {
        val scoreDifference = wrestlerRed.value.score - wrestlerBlue.value.score
        val leadToWin = wrestleStyle.value.leadToWin

        if (scoreDifference >= leadToWin) {
            wrestlerRed.value = wrestlerRed.value.copy(isWinner = true)
            wrestlerBlue.value = wrestlerBlue.value.copy(isWinner = false)
        } else if (scoreDifference <= -leadToWin) {
            wrestlerRed.value = wrestlerRed.value.copy(isWinner = false)
            wrestlerBlue.value = wrestlerBlue.value.copy(isWinner = true)
        } else {
            wrestlerRed.value = wrestlerRed.value.copy(isWinner = false)
            wrestlerBlue.value = wrestlerBlue.value.copy(isWinner = false)
        }
    }

    fun resetScores() {
        wrestlerRed.value = wrestlerRed.value.copy(score = 0, penalty = 0, isPassive = false, isWinner = false)
        wrestlerBlue.value = wrestlerBlue.value.copy(score = 0, penalty = 0, isPassive = false, isWinner = false)
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
        timer.value = timer.value.copy(isRunning = false)
    }

    fun stopTimer() {
        // Nur Runde erhöhen, wenn der Timer läuft
        // Nur Timer resetten, wenn nicht wrestle mode
        if (timer.value.isRunning) {
            setIsSoundPlaying(true)
            setPeriod(increment = true)
        }
        resetTimer()
    }

    fun resetTimer() {
        timerJob?.cancel()
        timer.value = defaultTimer.value
    }

    fun setTimer(state: TimerState, setDefault: Boolean = false) {
        timer.value = state

        if (setDefault) defaultTimer.value = state
    }

    private fun startTimerJob() {
        timer.value = timer.value.copy(isRunning = true)
        val timerValue = timer.value.minutes.toInt() * 60 + timer.value.seconds.toInt()

        timerJob = viewModelScope.coroutineScope.launch(Dispatchers.Main) {
            for (i in timerValue downTo 0) {
                timer.value = setFormatTimer(timer.value, i)

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