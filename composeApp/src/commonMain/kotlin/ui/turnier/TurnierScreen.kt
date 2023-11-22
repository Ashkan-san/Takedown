package ui.turnier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.Turnier
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import ui.scaffold.MyBottomBar
import ui.scaffold.MyTopBar

@Composable
fun TurnierScreen(
    navigator: Navigator,
    turniere: MutableList<Turnier>
) {
    Scaffold(
        topBar = {
            MyTopBar()
        },
        bottomBar = {
            MyBottomBar(navigator = navigator)
        },
    ) { innerPadding ->
        //TurnierScreen(innerPadding, fetch())
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
}

// TODO später eigenes Scaffold
@Composable
fun MyScaffold() {
}