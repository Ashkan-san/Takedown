package ui.scoreboard.punkte

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun PunkteSurface(
    modifier: Modifier,
    score: Int,
    onClick: () -> Unit,
    color: Color
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = color,
        contentColor = Color.White
    ) {
        Text(
            modifier = Modifier.clickable { onClick() },
            text = "$score",
            fontSize = 100.sp,
            textAlign = TextAlign.Center
        )
    }
}