package ui.scoreboard.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingTitle(title: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary)
    )
}