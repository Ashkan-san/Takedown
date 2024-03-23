package ui.tournaments.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun SearchFilterBar(
    onSearchChanged: (String) -> Unit,
    onShowFilters: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchField(
            modifier = Modifier.weight(1F),
            searchDisplay = "",
            onSearchChanged = {
                onSearchChanged(it)
            },
            onSearchClosed = {
                //searchQuery.value = ""
            }
        )

        // FILTER BUTTON
        IconButton(
            onClick = { onShowFilters() }
        ) {
            Icon(
                Icons.Default.FilterList,
                contentDescription = "Filter"
            )
        }
    }
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchDisplay: String,
    onSearchChanged: (String) -> Unit,
    onSearchClosed: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    when (expanded.value) {
        true -> ExpandedSearch(
            modifier = modifier,
            searchDisplay = searchDisplay,
            onSearchChanged = onSearchChanged,
            onSearchClosed = onSearchClosed,
            onExpandedChanged = { expanded.value = false }
        )

        false -> CollapsedSearch(
            onExpandedChanged = { expanded.value = true }
        )
    }

}

@Composable
fun CollapsedSearch(
    onExpandedChanged: () -> Unit
) {
    IconButton(
        onClick = { onExpandedChanged() }
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search"
        )
    }
}

// searchDisplay: der angezeigte Text
// onSearchChanged: String bei Änderung der Eingabe
// onSearchClosed: wenn Back Button gedrückt
// onExpandedChanged: wenn Back Button gedrückt, Modus wechseln
@Composable
fun ExpandedSearch(
    modifier: Modifier = Modifier,
    searchDisplay: String,
    onSearchChanged: (String) -> Unit,
    onSearchClosed: () -> Unit,
    onExpandedChanged: () -> Unit
) {
    // TODO textfield schiebt column etwas runter beim expanden, ändern
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }
    var textFieldValue = remember { mutableStateOf(TextFieldValue(searchDisplay, TextRange(searchDisplay.length))) }

    SideEffect {
        textFieldFocusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
            .focusRequester(textFieldFocusRequester),

        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
            onSearchChanged(it.text)
        },

        placeholder = { Text(text = "Search...") },
        leadingIcon = {
            IconButton(
                onClick = {
                    onExpandedChanged()
                    onSearchClosed()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Icon"
                )
            }
        },
        trailingIcon = {
            // Nur anzeigen, wenn Text nicht leer
            if (textFieldValue.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        textFieldValue.value = TextFieldValue("")
                        onSearchChanged("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Cancel"
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            /*focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,*/
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )

}