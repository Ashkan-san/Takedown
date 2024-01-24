package ui.scoreboard.timer

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import model.scoreboard.TimerState
import model.scoreboard.TimerType

@Composable
fun TimerTextField(
    modifier: Modifier,
    timerType: TimerType,
    timerValue: String,
    timerState: TimerState,
    imeAction: ImeAction,
    hideKeyboard: Boolean,
    onFocus: () -> Unit,
    onChange: (TimerState) -> Unit,
    onResetKeyboard: () -> Unit
) {
    var textFieldValue by remember(timerValue) {
        mutableStateOf(TextFieldValue(text = timerValue, selection = TextRange(timerValue.length)))
    }

    val edited = remember { mutableStateOf(false) }

    // für alles markieren bei Fokus
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // für Keyboard Options
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    if (hideKeyboard) {
        focusManager.clearFocus()
        keyboardController?.hide()
        onResetKeyboard()
    }

    LaunchedEffect(timerState.isRunning) {
        if (timerState.isRunning) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    // alles markieren, wenn angeclickt
    LaunchedEffect(isFocused, isPressed) {
        var endRange = 0

        if (isFocused) {
            endRange = textFieldValue.text.length
            onFocus()
        }

        textFieldValue = textFieldValue.copy(
            selection = TextRange(
                start = 0,
                end = endRange
            )
        )
    }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors,
        LocalTextToolbar provides EmptyTextToolbar
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                if (checkInput(it.text)) {
                    textFieldValue = it

                    // value im vm nur speichern, wenn nicht blank
                    if (it.text.isNotBlank()) {
                        when (timerType) {
                            TimerType.MIN -> {
                                onChange(timerState.copy(minutes = it.text))
                            }

                            TimerType.SEC -> {
                                onChange(timerState.copy(seconds = it.text))
                            }
                        }
                    }

                    // wenn 2 Zahlen eingegeben entweder zum nächsten Feld springen oder Keyboard schließen
                    // TODO später
                    /*if (edited.value && it.text.length == 2) {
                        println("$imeAction")
                        edited.value = false
                        when (imeAction) {
                            ImeAction.Next -> {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                            ImeAction.Done -> {
                                println(focusManager)
                                println(keyboardController)
                                //focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        }
                    }

                    edited.value = true*/
                }
            },

            modifier = modifier,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 120.sp,
                textAlign = TextAlign.Center,
                //letterSpacing = (-8).sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.Unspecified),

            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    textFieldValue = TextFieldValue(formatTimerState(timerValue))

                    focusManager.moveFocus(FocusDirection.Next)
                },
                onDone = {
                    textFieldValue = TextFieldValue(formatTimerState(timerValue))

                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            ),

            interactionSource = interactionSource
        )
    }
}

/**
 * String formatieren, wenn es eine Zahl zwischen 0 und 9 ist
 */
fun formatTimerState(value: String): String {
    val intString = value.toInt()
    return if (value.length == 1 && intString in 0..9) {
        "0$value"
    } else {
        value
    }
}

/**
 * Prüfen, ob der Input:
 * kleiner als Zahl 60 ist
 * Länge kleiner gleich 2
 * eine Zahl oder Empty Space ist
 */
fun checkInput(input: String): Boolean {
    val pattern = Regex("^\\d*\$")
    return (input.toIntOrNull() ?: 0) < 60 && input.length <= 2 && pattern.matches(input)
}

/*fun resetIfEmpty(input: String, timerState: String): TextFieldValue {
    return if (input.isBlank()) {
        TextFieldValue(timerState)
    } else {
        TextFieldValue(timerState)
    }
}*/

