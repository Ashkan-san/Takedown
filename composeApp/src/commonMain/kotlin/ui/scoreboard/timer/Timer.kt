package ui.scoreboard.timer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.scoreboard.TimerState

@Composable
fun Timer(
    timerState: TimerState,
    runningState: Boolean,
    onSelectTimer: () -> Unit,
    onChangeTimerMin: (String) -> Unit,
    onChangeTimerSec: (String) -> Unit,
    onClickPlay: () -> Unit,
    onClickStop: () -> Unit,
    onClickReset: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TimerTextField(
            timerState = timerState.minutes,
            runningState = runningState,
            modifier = Modifier.weight(1f),
            imeAction = ImeAction.Next,
            onSelect = onSelectTimer,
            onChange = onChangeTimerMin
        )
        Text(
            text = ":",
            fontSize = 100.sp,
            modifier = Modifier.border(1.dp, Color.Red, ShapeDefaults.Small)
        )
        TimerTextField(
            timerState = timerState.seconds,
            runningState = runningState,
            modifier = Modifier.weight(1f),
            imeAction = ImeAction.Done,
            onSelect = onSelectTimer,
            onChange = onChangeTimerSec
        )
    }

    TimerButtons(
        runningState = runningState,
        onClickPlay = onClickPlay,
        onClickStop = onClickStop,
        onClickReset = onClickReset
    )
}