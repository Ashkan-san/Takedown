package ui.scoreboard.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.scoreboard.TimerState
import model.scoreboard.TimerType

@Composable
fun Timer(
    timerState: TimerState,
    timerList: List<TimerState>,
    hideKeyboard: Boolean,
    isSoundPlaying: Boolean,
    onFocusTimer: () -> Unit,
    onTimerUpdate: (TimerState) -> Unit,
    onClickPlay: () -> Unit,
    onClickStop: () -> Unit,
    onClickReset: () -> Unit,
    onSetTimer: (TimerState) -> Unit,
    onResetKeyboard: () -> Unit,
    onSetPlaySound: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimerTextField(
            modifier = Modifier.weight(1f),
            timerType = TimerType.MIN,
            timerValue = timerState.minutes,
            timerState = timerState,
            imeAction = ImeAction.Next,
            hideKeyboard = hideKeyboard,
            onFocus = onFocusTimer,
            onChange = onTimerUpdate,
            onResetKeyboard = onResetKeyboard
        )

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = ":",
                fontSize = 120.sp,
                textAlign = TextAlign.Center,
            )
        }
        TimerTextField(
            modifier = Modifier.weight(1f),
            timerType = TimerType.SEC,
            timerValue = timerState.seconds,
            timerState = timerState,
            imeAction = ImeAction.Done,
            hideKeyboard = hideKeyboard,
            onFocus = onFocusTimer,
            onChange = onTimerUpdate,
            onResetKeyboard = onResetKeyboard
        )
    }

    TimerButtons(
        runningState = timerState.isRunning,
        timerList = timerList,
        isSoundPlaying = isSoundPlaying,
        onClickPlay = onClickPlay,
        onClickStop = onClickStop,
        onClickReset = onClickReset,
        onSetTimer = onSetTimer,
        onSetPlaySound = onSetPlaySound
    )

}