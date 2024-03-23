package data.model.fundamentals

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

data class Fundamental(
    val title: StringResource,
    val icon: ImageVector,
    val navigation: String
)