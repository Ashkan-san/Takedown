package previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import takedown.composeapp.generated.resources.Res
import takedown.composeapp.generated.resources.rules_general
import takedown.composeapp.generated.resources.rules_points
import takedown.composeapp.generated.resources.rules_screen
import takedown.composeapp.generated.resources.rules_summary
import takedown.composeapp.generated.resources.wrestling_styles

@Preview
@Composable
fun RulesScreen() {
    //val styles = stringArrayResource(Res.string.wrestling_styles).toList()
    val types = remember { listOf("Olympia", "World Cup", "Germany") }

    val ruleSubjects2 = listOf(
        RulesSubject(
            stringResource(Res.string.rules_general),
            Icons.Default.SportsKabaddi,
            "/ruleGeneral"
        ),
        RulesSubject(
            stringResource(Res.string.rules_points),
            Icons.Default.SportsBar,
            "/rulePoints"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.rules_screen)) }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Text Bereich mit Überschrift und Content
            Column {
                Text(stringResource(Res.string.rules_summary))
            }

            HorizontalDivider()

            // Dropdowns
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RuleDropdown(
                    modifier = Modifier.weight(1f),
                    items = types
                )
                RuleDropdown(
                    modifier = Modifier.weight(1f),
                    items = types
                )
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Kacheln mit Überschrift und Icon
                ruleSubjects2.forEach {
                    RuleCard(
                        modifier = Modifier,
                        title = it.title,
                        icon = it.icon
                    )
                }
            }
        }
    }
}

data class RulesSubject(
    val title: String,
    val icon: ImageVector,
    val navigation: String
)

@Composable
fun RuleDropdown(
    modifier: Modifier,
    items: List<String>
) {
    var expanded by remember { mutableStateOf(true) }
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

@Composable
fun RuleCard(
    modifier: Modifier,
    title: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title)

            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        }
    }
}