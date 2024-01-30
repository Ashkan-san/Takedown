package ui.scoreboard.timer

import PlayWhistle
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WhistleButton(
    isSoundPlaying: Boolean,
    onSetPlaySound: (Boolean) -> Unit
) {

    IconButton(
        onClick = {
            onSetPlaySound(true)
        }
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Default.Sports,
            contentDescription = "Whistle Icon"
        )
    }

    if (isSoundPlaying) {
        PlayWhistle()
        onSetPlaySound(false)
    }
}


