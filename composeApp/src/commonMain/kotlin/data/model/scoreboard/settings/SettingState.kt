package data.model.scoreboard.settings

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingState(
    val type: SettingType,
    val title: String,
    val description: String,
    val altDescription: String?,
    val icon: ImageVector?,
    val iconDescription: String?,
    val function: () -> Unit
)