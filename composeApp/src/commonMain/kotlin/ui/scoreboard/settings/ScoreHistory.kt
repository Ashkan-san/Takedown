package ui.scoreboard.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.scoreboard.score.Score

@Composable
fun ScoreHistory(
    scoreHistory: List<Score>
) {
    SettingTitle(title = "Scorehistory")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .horizontalScroll(rememberScrollState())
    ) {
        scoreHistory.forEachIndexed { _, item ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 2.dp)
                    .background(item.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${item.scoreValue}",
                    style = MaterialTheme.typography.displaySmall.copy(color = Color.White)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}