package ui.scaffold

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MyTopBar() {
    CenterAlignedTopAppBar(
        //backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {},
        title = {
            Icon(
                painter = painterResource("logo.png"),
                contentDescription = "Top Bar Icon",
                modifier = Modifier.size(80.dp),
                tint = Color.Unspecified
            )
        }
    )
}