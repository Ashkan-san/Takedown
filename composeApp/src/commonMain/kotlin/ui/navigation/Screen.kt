package ui.navigation

sealed class Screen(var title: String, var route: String) {

    object TurnierDetails : Screen("Turnier Details", "/turnierDetails")
    object TurnierRanking : Screen("Turnier Ranking", "/turnierRanking")
    object Scoreboard : Screen("Scoreboard", "/scoreboard")

}