package ui.fundamentals

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import commons.theme.TakedownTheme
import data.model.fundamentals.Fundamental
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import takedown.composeapp.generated.resources.*
import takedown.composeapp.generated.resources.Res
import takedown.composeapp.generated.resources.fundamentals_general
import takedown.composeapp.generated.resources.fundamentals_points
import takedown.composeapp.generated.resources.fundamentals_tournament

@Composable
@Preview
fun FundamentalsContentPreview() {
    TakedownTheme {
        FundamentalsContent(
            fundamentalsList =
            listOf(
                Fundamental(
                    Res.string.fundamentals_general,
                    Icons.AutoMirrored.Filled.MenuBook,
                    "/fundamentalsGeneral"
                ),
                Fundamental(
                    Res.string.fundamentals_points,
                    Icons.Default.Scoreboard,
                    "/fundamentalsPoints"
                ),
                Fundamental(
                    Res.string.fundamentals_tournament,
                    Icons.Default.EmojiEvents,
                    "/fundamentalsTournament"
                ),
                Fundamental(
                    Res.string.fundamentals_bout,
                    Icons.Default.SportsKabaddi,
                    "/fundamentalsBout"
                ),
                Fundamental(
                    Res.string.fundamentals_passive,
                    Icons.Default.Block,
                    "/fundamentalsPassive"
                ),
                Fundamental(
                    Res.string.fundamentals_people,
                    Icons.Default.Groups,
                    "/fundamentalsPeople"
                ),
                Fundamental(
                    Res.string.fundamentals_tips,
                    Icons.Default.Lightbulb,
                    "/fundamentalsTips"
                )
            ),
            onClickCard = {}
        )
    }
}

@Composable
fun FundamentalsContent(
    fundamentalsList: List<Fundamental>,
    onClickCard: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(fundamentalsList) { fundamental ->
            FundamentalCard(
                modifier = Modifier,
                item = fundamental,
                onClickCard = onClickCard
            )
        }
    }
}

@Composable
fun FundamentalCard(
    modifier: Modifier = Modifier,
    item: Fundamental,
    onClickCard: (String) -> Unit = {}
) {

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClickCard(item.navigation) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(resource = item.title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                //color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall
            )

            Icon(
                imageVector = item.icon,
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