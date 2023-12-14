package ui.turnier

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import data.Turnier

@Composable
fun TurnierDetailsErgebnisse(aktuellesTurnier: Turnier) {
    Column {
        aktuellesTurnier.platzierungen.forEachIndexed { index, platzierung ->
            Text(platzierung.ringerName)
        }
    }
}