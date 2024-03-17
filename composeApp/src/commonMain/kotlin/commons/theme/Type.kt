package commons.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Display: große kurze Texte
// Headline: kleiner als Display, auch kurze Überschriften
// Title: nochmal kleiner, für Medium großen Text
// Body: für Paragraphen
// Label: für sehr kleine Texte, z.B. in Buttons auch

val Typography = Typography(
    // Scoreboard Timer und Score
    displayLarge = TextStyle(
        fontSize = 120.sp,
        textAlign = TextAlign.Center,
    ),
    // Scoreboard Info Buttons
    labelMedium = TextStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    // Top Bar
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    )
)