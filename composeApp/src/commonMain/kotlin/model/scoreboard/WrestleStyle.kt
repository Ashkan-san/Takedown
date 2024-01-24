package model.scoreboard

data class WrestleStyle(
    val name: String,
    val abbreviation: String,
    val leadToWin: Int,
    val weightClasses: List<Int>
)