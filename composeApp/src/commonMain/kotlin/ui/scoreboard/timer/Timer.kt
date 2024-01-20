package ui.scoreboard.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
    hideKeyboard: Boolean,
    onFocusTimer: () -> Unit,
    onTimerUpdate: (TimerState) -> Unit,
    onClickPlay: () -> Unit,
    onClickStop: () -> Unit,
    onClickReset: () -> Unit,
    onSetTimer: (TimerState) -> Unit,
    onResetKeyboard: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 50.dp),
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
        Text(
            text = ":",
            fontSize = 100.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
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
        onClickPlay = onClickPlay,
        onClickStop = onClickStop,
        onClickReset = onClickReset,
        onSetTimer = onSetTimer
    )
}