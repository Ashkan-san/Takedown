package ui.scoreboard.settings

import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import model.scoreboard.SettingState

@Composable
fun Settings(
    wrestleModeSettings: List<SettingState>,
    resetSettings: List<SettingState>,
    soundSettings: List<SettingState>,
    modeState: SettingState,
    playSound: Boolean,
    onSetWrestleMode: (SettingState) -> Unit
) {
    // Wrestling Style

    // Wrestle Mode
    SettingsBox(
        title = "Wrestle Mode"
    ) {
        wrestleModeSettings.forEach { mode ->
            SelectorSetting(
                title = mode.title,
                description = mode.description,
                leading = {
                    RadioButton(
                        selected = (mode == modeState),
                        onClick = { onSetWrestleMode(mode) }
                    )
                },
                onClick = { onSetWrestleMode(mode) }
            )
        }
    }

    // Appearance

    // Sound
    SettingsBox(
        title = "Sound"
    ) {
        soundSettings.forEach { setting ->
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
                onClick = { setting.function() }
            )
        }
    }

    // Reset
    SettingsBox(
        title = "Reset"
    ) {
        resetSettings.forEach { setting ->
            SelectorSetting(
                title = setting.title,
                description = setting.description,
                leading = {
                    Icon(setting.icon!!, setting.iconDescription)
                },
                onClick = {
                    setting.function()
                }
            )
        }
    }
}