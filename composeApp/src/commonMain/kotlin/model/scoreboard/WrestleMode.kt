package model.scoreboard

enum class WrestleModeType {
    CLASSIC, TOURNAMENT, INFINITE
}

data class WrestleMode(
    val title: String,
    val description: String,
    val type: WrestleModeType
)