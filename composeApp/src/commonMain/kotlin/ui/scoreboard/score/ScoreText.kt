package ui.scoreboard.score

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

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
        overflow = TextOverflow.Visible,
        softWrap = false,
        style = MaterialTheme.typography.displayLarge.copy(
            fontSize = MaterialTheme.typography.displayLarge.fontSize * multiplier.value
        ),
        onTextLayout = {
            if (it.hasVisualOverflow) {
                multiplier.value *= 0.99f
            }
        }
    )
}