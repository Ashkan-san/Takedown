import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import data.Turnier
import data.TurnierAlterGewichtKlasse

actual suspend fun fetchAllTurniere(): MutableList<Turnier> {
    TODO("Not yet implemented")
}

actual suspend fun fetchAlterGewichtKlassen(turnier: Turnier): MutableList<TurnierAlterGewichtKlasse> {
    TODO("Not yet implemented")
}

actual suspend fun fetchDetails(turnier: Turnier): Turnier {
    TODO("Not yet implemented")
}

@Composable
actual fun getTakedownLogo(): Painter {
    TODO("Not yet implemented")
}

@Composable
actual fun getTurnierBild(): Painter {
    TODO("Not yet implemented")
}