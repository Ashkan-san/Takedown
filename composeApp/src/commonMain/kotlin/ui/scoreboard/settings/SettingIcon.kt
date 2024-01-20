package ui.scoreboard.settings

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingIcon(
    icon: ImageVector
) {
    Icon(
        modifier = Modifier.size(24.dp),
        imageVector = icon,
        contentDescription = "Settings Icon"
    )
}