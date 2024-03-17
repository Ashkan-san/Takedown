package ui.scoreboard.score

import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import commons.ui.iconButtonModifier

@Composable
fun WinnerIcon(
    isWinner: Boolean
) {
    Crossfade(targetState = isWinner) { value ->
        Icon(
            modifier = iconButtonModifier,
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Winner Icon",
            tint = if (value) Color.Yellow else Color.Transparent
        )
    }

}