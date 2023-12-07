package ui.scaffold

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import getTakedownLogo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    CenterAlignedTopAppBar(
        //backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {},
        title = {
            IconButton(
                onClick = {},
                enabled = false,
                modifier = Modifier.size(100.dp),
            ) {
                Icon(
                    painter = getTakedownLogo(),
                    contentDescription = "Top Bar Icon",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            }
        }
    )
}