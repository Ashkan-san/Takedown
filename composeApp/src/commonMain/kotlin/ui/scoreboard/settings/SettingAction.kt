package ui.scoreboard.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectorSetting(
    title: String,
    description: String,
    leading: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {},
    isLast: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 5.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leading()

        Spacer(modifier = Modifier.width(10.dp))

        // Text
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        trailing()
    }
    if (!isLast) HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
}
