package de.takedown.app

import App
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowInsetsControllerCompat
import ui.scoreboard.ScoreboardViewModel
import ui.turnier.TurnierViewModel

class MainActivity : ComponentActivity() {
    private val turnierViewModel by viewModels<TurnierViewModel>()
    private val scoreboardViewModel by viewModels<ScoreboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)
        )

        super.onCreate(savedInstanceState)

        setContent {
            /*// Dynamic color is available on Android 12+
            val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            val darkTheme = isSystemInDarkTheme()
            val colors = when {
                dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
                dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }*/

            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isSystemInDarkTheme().not()
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = isSystemInDarkTheme().not()
            App(turnierViewModel = turnierViewModel, scoreboardViewModel = scoreboardViewModel)
        }
    }
}
