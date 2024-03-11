package de.takedown.app

import App
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)
        )

        super.onCreate(savedInstanceState)

        setContent {
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isSystemInDarkTheme().not()
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = isSystemInDarkTheme().not()

            App()
        }
    }
}