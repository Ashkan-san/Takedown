package previews.fundamentals

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.SportsKabaddi
import com.rickclephas.kmm.viewmodel.KMMViewModel
import de.takedown.app.R

class FundamentalsViewModel : KMMViewModel() {
    // TODO nicht navigate, sondern anderer content im selben screen dann
    val fundamentalsList = listOf(
        Fundamental(
            R.string.fundamentals_general,
            Icons.AutoMirrored.Filled.MenuBook,
            "/fundamentalsGeneral"
        ),
        Fundamental(
            R.string.fundamentals_points,
            Icons.Default.Scoreboard,
            "/fundamentalsPoints"
        ),
        Fundamental(
            R.string.fundamentals_tournament,
            Icons.Default.EmojiEvents,
            "/fundamentalsTournament"
        ),
        Fundamental(
            R.string.fundamentals_bout,
            Icons.Default.SportsKabaddi,
            "/fundamentalsBout"
        ),
        Fundamental(
            R.string.fundamentals_passive,
            Icons.Default.Block,
            "/fundamentalsPassive"
        ),
        Fundamental(
            R.string.fundamentals_people,
            Icons.Default.Groups,
            "/fundamentalsPeople"
        ),
        Fundamental(
            R.string.fundamentals_tips,
            Icons.Default.Lightbulb,
            "/fundamentalsTips"
        )
    )
}