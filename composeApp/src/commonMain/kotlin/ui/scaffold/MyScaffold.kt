package ui.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MyScaffold(
    navigator: Navigator,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            MyTopBar()
        },
        bottomBar = {
            MyBottomBar(navigator = navigator)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}