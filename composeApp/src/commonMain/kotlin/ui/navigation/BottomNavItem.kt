package ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {

    object Tournaments : BottomNavItem("Home", Icons.Default.Home, "/tournaments")
    object Rules : BottomNavItem("Rules", Icons.Default.MenuBook, "/rules")
    object Moves : BottomNavItem("Moves", Icons.Default.SportsKabaddi, "/moves")
    object Scoreboard : BottomNavItem("Scoreboard", Icons.Default.Scoreboard, "/scoreboard")
    object Account : BottomNavItem("Account", Icons.Default.AccountCircle, "/account")
}