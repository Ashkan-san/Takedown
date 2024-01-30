package ui.scoreboard.timer

import PlayWhistle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.scoreboard.TimerState
import ui.util.buttonModifier

@Composable
fun TimerButtons(
    runningState: Boolean,
    timerList: List<TimerState>,
    isSoundPlaying: Boolean,
    onClickPlay: () -> Unit,
    onClickStop: () -> Unit,
    onClickReset: () -> Unit,
    onSetTimer: (TimerState) -> Unit,
    onSetPlaySound: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { onClickPlay() }
        ) {
            Icon(
                modifier = buttonModifier,
                imageVector = if (runningState) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "Play/Pause Icon"
            )
        }

        IconButton(
            onClick = { onClickStop() }
        ) {
            Icon(
                modifier = buttonModifier,
                imageVector = Icons.Default.Stop,
                contentDescription = "Stop Icon"
            )
        }

        IconButton(
            onClick = { onClickReset() }
        ) {
            Icon(
                modifier = buttonModifier,
                imageVector = Icons.Default.Replay,
                contentDescription = "Reset Icon"
            )
        }

        TimerDropdown(
            timerList = timerList,
            onClickItem = onSetTimer
        )

        // Whistle Button
        IconButton(
            onClick = {
                onSetPlaySound(true)
            }
        ) {
            Icon(
                modifier = buttonModifier,
                imageVector = Icons.Default.Sports,
                contentDescription = "Whistle Icon"
            )

            if (isSoundPlaying) {
                PlayWhistle()
                onSetPlaySound(false)
            }
        }
    }
}
