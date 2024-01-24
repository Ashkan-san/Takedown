package ui.scoreboard.score

import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ScoreText(
    text: String,
    onClick: () -> Unit
) {
    val multiplier = remember { mutableStateOf(1f) }

    Text(
        modifier = Modifier.clickable { onClick() },
        text = text,
        fontSize = 120.sp,
        maxLines = 1,
        softWrap = false,
        textAlign = TextAlign.Center,
        style = LocalTextStyle.current.copy(
            fontSize = 110.sp * multiplier.value
        ),
        onTextLayout = {
            if (it.hasVisualOverflow) {
                multiplier.value *= 0.99f
            }
        }
    )
}