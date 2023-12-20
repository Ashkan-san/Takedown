package ui.navigation

sealed class Screen(var title: String, var route: String) {

    //object Turnier : Screen("Turnier", "/turnier")
    object TurnierDetails : Screen("Turnier Details", "/turnierDetails")
    object TurnierRanking : Screen("Turnier Ranking", "/turnierRanking")

}