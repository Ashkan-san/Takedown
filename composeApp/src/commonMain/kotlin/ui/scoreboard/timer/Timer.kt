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

@Composable
fun Timer(
    timerState: TimerState,
    hideKeyboard: Boolean,
    onFocusTimer: () -> Unit,
    onChangeTimerMin: (String) -> Unit,
    onChangeTimerSec: (String) -> Unit,
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
            timerState = timerState.minutes,
            runningState = timerState.isRunning,
            modifier = Modifier.weight(1f),
            imeAction = ImeAction.Next,
            hideKeyboard = hideKeyboard,
            onFocus = onFocusTimer,
            onChange = onChangeTimerMin,
            onResetKeyboard = onResetKeyboard
        )
        Text(
            text = ":",
            fontSize = 100.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        TimerTextField(
            timerState = timerState.seconds,
            runningState = timerState.isRunning,
            modifier = Modifier.weight(1f),
            imeAction = ImeAction.Done,
            hideKeyboard = hideKeyboard,
            onFocus = onFocusTimer,
            onChange = onChangeTimerSec,
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