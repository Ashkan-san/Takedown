package data.model.scoreboard.timer

data class TimerState(
    val minutes: String = "03",
    val seconds: String = "00",
    val isRunning: Boolean = false
) {
    override fun toString(): String {
        return "$minutes:$seconds"
    }
}