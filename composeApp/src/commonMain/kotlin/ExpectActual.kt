import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import data.Turnier
import data.TurnierAlterGewichtKlasse

expect suspend fun fetchAllTurniere(): MutableList<Turnier>

expect suspend fun fetchAlterGewichtKlassen(turnier: Turnier): MutableList<TurnierAlterGewichtKlasse>

expect suspend fun fetchDetails(turnier: Turnier): Turnier

@Composable
expect fun Maps(address: String)