package ui.scoreboard.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import model.scoreboard.score.Score
import model.scoreboard.settings.SettingState

@Composable
fun Settings(
    scoreHistory: List<Score>,
    wrestleModeSettings: List<SettingState>,
    soundSettings: List<SettingState>,
    resetSettings: List<SettingState>,
    currentMode: SettingState,
    playSound: Boolean,
    onSetMode: (SettingState) -> Unit
) {

    ScoreHistory(scoreHistory)

    // Wrestle Mode
    SettingSurface(
        title = "Wrestle Mode"
    ) {
        Column {
            wrestleModeSettings.forEachIndexed { index, mode ->
                SelectorSetting(
                    title = mode.title,
                    description = mode.description,
                    leading = {
                        RadioButton(
                            selected = (mode == currentMode),
                            onClick = { onSetMode(mode) }
                        )
                    },
                    isLast = index == wrestleModeSettings.lastIndex,
                    onClick = { onSetMode(mode) }
                )
            }
        }
    }

    // Appearance

    // Sound
    SettingSurface(
        title = "Sound"
    ) {
        Column {
            soundSettings.forEachIndexed { index, setting ->
                SelectorSetting(
                    title = setting.title,
                    description = setting.description,
                    leading = {
                        Icon(setting.icon!!, setting.iconDescription)
                    },
                    trailing = {
                        Switch(
                            modifier = Modifier.scale(0.8f),
                            checked = playSound,
                            onCheckedChange = {
                                setting.function()
                            }
                        )
                    },
                    isLast = index == soundSettings.lastIndex,
                    onClick = { setting.function() }
                )
            }
        }
    }

    // Reset
    SettingSurface(
        title = "Reset"
    ) {
        Column {
            resetSettings.forEachIndexed { index, setting ->
                SelectorSetting(
                    title = setting.title,
                    description = setting.description,
                    leading = {
                        Icon(setting.icon!!, setting.iconDescription)
                    },
                    isLast = index == resetSettings.lastIndex,
                    onClick = {
                        setting.function()
                    }
                )
            }
        }
    }
}