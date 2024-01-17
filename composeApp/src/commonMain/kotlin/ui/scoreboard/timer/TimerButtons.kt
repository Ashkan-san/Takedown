package ui.scoreboard.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimerButtons(
    runningState: Boolean,
    onClickPlay: () -> Unit,
    onClickStop: () -> Unit,
    onClickReset: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { onClickPlay() }
        ) {
            Icon(
                imageVector = if (runningState) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "Play/Pause Icon"
            )
        }

        IconButton(
            onClick = { onClickStop() }
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Default.Stop,
                contentDescription = "Stop Icon"
            )
        }

        IconButton(
            onClick = { onClickReset() }
        ) {
            Icon(
                imageVector = Icons.Default.Replay,
                contentDescription = "Reset Icon"
            )
        }

    }
}
