package ui.turnier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.Turnier
import org.jetbrains.compose.resources.painterResource

@Composable
fun TurnierScreen(
    innerPadding: PaddingValues,
    turniere: List<Turnier>
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        turniere.forEach { turnier ->
            TurnierCard(turnier = turnier)
        }
    }
}