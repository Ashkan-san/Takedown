package ui.navigation

sealed class Screen(var title: String, var route: String) {

    object TournamentDetails : Screen("Tournament Details", "/tournamentDetails")
    object TournamentRanking : Screen("Tournament Ranking", "/tournamentRanking")
    object Scoreboard : Screen("Scoreboard", "/scoreboard")

}