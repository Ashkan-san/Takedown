package ui.turnier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import ui.scaffold.MyBottomBar
import ui.scaffold.MyScaffold
import ui.scaffold.MyTopBar
import ui.scaffold.getTakedownLogo
import viewmodel.TurnierViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnierDetailsScreen(navigator: Navigator, viewModel: TurnierViewModel) {
    val aktuellesTurnier = remember { viewModel.aktuellesTurnier }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() },
                        //modifier = Modifier.size(100.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Top Bar Back Icon",
                            //modifier = Modifier.size(100.dp),
                            //tint = Color.Unspecified
                        )
                    }
                },
                title = { Text("") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            aktuellesTurnier.value!!.turnierDetails.forEach {
                println("Test: ${it.altersKlasse}, ${it.geschlecht}, ${it.modus}, ${it.jahrgaenge}, ${it.stilart}, ${it.gewichtsKlassen}")
                Text(text = "${it.altersKlasse}, ${it.geschlecht}, ${it.modus}, ${it.jahrgaenge}, ${it.stilart}, ${it.gewichtsKlassen}")
            }
        }
    }
}