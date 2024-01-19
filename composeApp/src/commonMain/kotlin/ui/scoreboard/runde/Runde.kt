package ui.scoreboard.runde

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun Runde(
    roundState: Int
) {
    Text(
        text = "Runde $roundState",
        fontSize = 25.sp
    )
}