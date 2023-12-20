import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import data.Turnier
import data.TurnierAlterGewichtKlasse
import viewmodel.TurnierViewModel

expect suspend fun fetchAllTurniere(): MutableList<Turnier>

expect suspend fun fetchAlterGewichtKlassen(turnier: Turnier): MutableList<TurnierAlterGewichtKlasse>

expect suspend fun fetchDetails(turnier: Turnier): Turnier

@Composable
expect fun Maps(address: String)