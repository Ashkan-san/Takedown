package commons.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    var title: String,
    var route: String,
    var icon: ImageVector? = null
) {
    object Tournaments : NavItem("Home", "/tournaments", Icons.Default.Home)
    object Fundamentals : NavItem("Fundamentals", "/fundamentals", Icons.AutoMirrored.Filled.MenuBook)
    object Moves : NavItem("Moves", "/moves",  Icons.Default.SportsKabaddi)
    object Scoreboard : NavItem("Scoreboard", "/scoreboard", Icons.Default.Scoreboard)
    object Account : NavItem("Account", "/account", Icons.Default.AccountCircle)

    object TournamentDetails : NavItem("Tournament Details", "/tournamentDetails")
    object TournamentRanking : NavItem("Tournament Ranking", "/tournamentRanking")

    object FundamentalDetails : NavItem("Fundamental Details", "/fundamentalDetails")
}