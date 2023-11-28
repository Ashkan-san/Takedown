package ui.navigation

sealed class Screen(var title: String, var route: String) {

    object TurnierDetails : Screen("Turnier", "/turnier")

}