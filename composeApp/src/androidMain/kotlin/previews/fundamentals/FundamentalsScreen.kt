package previews.fundamentals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import commons.navigation.NavItem
import commons.theme.TakedownTheme
import commons.ui.scaffold.BackAndSettingTopBar
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
@Preview(showSystemUi = true)
fun Test() {
    TakedownTheme {
        FundamentalsScreen(
            viewModel = FundamentalsViewModel()
        )
    }
}

@Composable
fun FundamentalsScreen(
    modifier: Modifier = Modifier,
    viewModel: FundamentalsViewModel = koinInject(),
    onBack: () -> Unit = {},
    onClickCard: (String) -> Unit = {},
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BackAndSettingTopBar(
                title = NavItem.Fundamentals.title,
                onClickBack = { onBack() },
                onClickSettings = { showBottomSheet = true }
            )
        },
    ) { innerPadding ->
        FundamentalCardsGrid(
            fundamentalsList = viewModel.fundamentalsList,
            onClickCard = onClickCard,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Composable
fun FundamentalCardsGrid(
    fundamentalsList: List<Fundamental>,
    onClickCard: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(fundamentalsList) { item ->
            FundamentalCard(
                modifier = Modifier,
                title = stringResource(id = item.title),
                icon = item.icon,
                navigation = item.navigation,
                onClickCard = onClickCard
            )
        }
    }
}

@Composable
fun FundamentalCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    navigation: String,
    onClickCard: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClickCard(navigation) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                //color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall
            )

            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun RuleDropdown(
    modifier: Modifier,
    items: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf(items[0]) }

    Box(
        modifier = modifier.padding(5.dp),
    ) {
        TextField(
            value = currentItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        currentItem = it
                    }
                )
            }
        }
    }
}