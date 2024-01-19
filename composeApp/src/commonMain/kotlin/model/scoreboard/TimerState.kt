package model.scoreboard

data class TimerState(
    val minutes: String,
    val seconds: String,
) {
    override fun toString(): String {
        return "$minutes:$seconds"
    }
}