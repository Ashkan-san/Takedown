package model.scoreboard

import androidx.compose.ui.graphics.vector.ImageVector

enum class SettingType {
    MODE, SOUND, RESET
}

data class SettingState(
    val type: SettingType,
    val title: String,
    val description: String,
    val altDescription: String?,
    val icon: ImageVector?,
    val iconDescription: String?,
    val function: () -> Unit
)