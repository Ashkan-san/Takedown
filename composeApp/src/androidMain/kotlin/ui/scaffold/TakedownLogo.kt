package ui.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import de.takedown.app.R

@Composable
actual fun getTakedownLogo(): Painter {
    return painterResource(R.drawable.ic_launcher_foreground)
}