package model.scoreboard.score

data class WrestlerState(
    val colorName: WrestlerColor,
    val score: Score,
    val penalty: Int = 0,
    val isPassive: Boolean = false,
    val isWinner: Boolean = false
)