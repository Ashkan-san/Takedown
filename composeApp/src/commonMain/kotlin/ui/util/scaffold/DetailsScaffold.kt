package ui.util.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun DetailsScaffold(
    navigator: Navigator,
    title: String,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
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
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
