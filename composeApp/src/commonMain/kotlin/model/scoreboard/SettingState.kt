package model.scoreboard

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingState(
    val title: String,
    val description: String,
    val altDescription: String?,
    val icon: ImageVector?,
    val iconDescription: String?,
    val function: () -> Unit,
)