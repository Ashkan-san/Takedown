package ui.scoreboard.score

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ScoreText(
    text: String,
    onClick: () -> Unit
) {
    val multiplier = remember { mutableStateOf(1f) }

    Text(
        modifier = Modifier.clickable { onClick() },
        text = text,
        maxLines = 1,
        softWrap = false,
        style = MaterialTheme.typography.displayLarge
    )
}