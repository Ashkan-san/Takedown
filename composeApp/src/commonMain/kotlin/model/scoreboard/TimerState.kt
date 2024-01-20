package model.scoreboard

enum class TimerType {
    MIN, SEC
}

data class TimerState(
    val minutes: String = "03",
    val seconds: String = "00",
    val isRunning: Boolean = false
) {
    override fun toString(): String {
        return "$minutes:$seconds"
    }
}