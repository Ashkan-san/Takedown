package model.scoreboard.details

data class WrestleStyle(
    val name: String,
    val abbreviation: String,
    val weightClasses: List<Int>,

    val leadToWin: Int,

    val periods: List<Int> = (1..10).toList(),
    val period: Int = periods[0],
    val weight: String = "${weightClasses[0]} kg",
)