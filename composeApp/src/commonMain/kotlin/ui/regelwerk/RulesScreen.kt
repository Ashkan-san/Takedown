package ui.regelwerk

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator
import ui.turnier.TurnierViewModel

@Composable
fun RulesScreen(navigator: Navigator, viewModel: TurnierViewModel) {
    viewModel.addExample()

    Column() {
        Text("test")
    }
}