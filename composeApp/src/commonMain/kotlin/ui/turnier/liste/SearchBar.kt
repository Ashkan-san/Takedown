package ui.turnier.liste

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
fun SearchBar(
    modifier: Modifier,
    searchDisplay: String,
    onSearchChanged: (String) -> Unit,
    onSearchClosed: () -> Unit
) {
    val (expanded, onExpandedChanged) = remember { mutableStateOf(false) }

    when (expanded) {
        true -> ExpandedSearch(
            modifier = modifier,
            searchDisplay = searchDisplay,
            onSearchChanged = onSearchChanged,
            onSearchClosed = onSearchClosed,
            onExpandedChanged = onExpandedChanged
        )

        false -> CollapsedSearch(
            onExpandedChanged = onExpandedChanged
        )
    }

}

@Composable
fun CollapsedSearch(
    onExpandedChanged: (Boolean) -> Unit
) {
    IconButton(
        onClick = { onExpandedChanged(true) }
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
    onExpandedChanged: (Boolean) -> Unit
) {
    // TODO textfield schiebt column etwas runter beim expanden, ändern
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }
    val textFieldValue = remember { mutableStateOf(TextFieldValue(searchDisplay, TextRange(searchDisplay.length))) }

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

        placeholder = { Text(text = "Suchen...") },
        leadingIcon = {
            IconButton(
                onClick = {
                    onExpandedChanged(false)
                    onSearchClosed()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
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