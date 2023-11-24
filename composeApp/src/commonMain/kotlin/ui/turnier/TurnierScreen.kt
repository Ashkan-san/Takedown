package ui.turnier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.Turnier
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import ui.scaffold.MyBottomBar
import ui.scaffold.MyTopBar
import viewmodel.TurnierViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun TurnierScreen(
    navigator: Navigator,
    viewModel: TurnierViewModel
) {
    //val turniere2 by remember { mutableStateOf(viewModel.turniere) }
    val turniere = remember { viewModel.turniere }
    val isLoading = viewModel.isLoading


    Scaffold(
        topBar = {
            MyTopBar()
        },
        bottomBar = {
            MyBottomBar(navigator = navigator)
        },
        /*floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addTurnier() }) {
                Icon(Icons.Filled.Add, "")
            }
        }*/
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                isLoading.value -> LoadingUi()
                else -> turniere.forEach { turnier ->
                    println(turnier)
                    TurnierCard(turnier = turnier)
                }
            }
        }
    }
}

@Composable
fun LoadingUi() {
    CircularProgressIndicator(
        modifier = Modifier.width(60.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

// TODO später eigenes Scaffold
@Composable
fun MyScaffold() {
}