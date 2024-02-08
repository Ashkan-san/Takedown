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
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
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

        /* val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
         // Configure the behavior of the hidden system bars.
         windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

         // Add a listener to update the behavior of the toggle fullscreen button when
         // the system bars are hidden or revealed.
         window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
             // You can hide the caption bar even when the other system bars are visible.
             // To account for this, explicitly check the visibility of navigationBars()
             // and statusBars() rather than checking the visibility of systemBars().
             if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars()) || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
                 windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

                *//* binding.toggleFullscreenButton.setOnClickListener {
                    // Hide both the status bar and the navigation bar.
                }*//*
            } else {
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())

                *//*binding.toggleFullscreenButton.setOnClickListener {
                    // Show both the status bar and the navigation bar.
                }*//*
            }
            view.onApplyWindowInsets(windowInsets)
        }*/

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
