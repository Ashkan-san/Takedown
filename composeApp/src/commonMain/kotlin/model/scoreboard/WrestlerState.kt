package model.scoreboard

import androidx.compose.ui.graphics.Color

data class WrestlerState(
    val colorName: WrestlerColor,
    val color: Color,
    val score: Int = 0,
    val penalty: Int = 0,
    val isPassive: Boolean = false,
    val isWinner: Boolean = false
)

enum class WrestlerColor {
    BLUE, RED
}