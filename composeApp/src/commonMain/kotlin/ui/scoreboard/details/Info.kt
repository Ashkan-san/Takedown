package ui.scoreboard.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.scoreboard.details.WrestleDetails
import model.scoreboard.details.WrestleStyle

@Composable
fun Info(
    style: WrestleStyle,
    styles: List<WrestleStyle>,
    wrestleDetails: WrestleDetails,
    onClickStyle: (WrestleStyle) -> Unit,
    onClickPeriod: (Int) -> Unit,
    onClickWeight: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailDropdown(
                modifier = Modifier.weight(1f),
                type = "STYLE",
                currentValue = wrestleDetails.style,
                styles = styles,
                onSelectStyle = onClickStyle
            )
            DetailDropdown(
                modifier = Modifier,
                type = "PERIOD",
                periods = style.periods,
                currentValue = wrestleDetails.period,
                onSelectPeriod = onClickPeriod
            )
            DetailDropdown(
                modifier = Modifier.weight(1f),
                type = "WEIGHT",
                weights = style.weightClasses.map { "$it kg" },
                currentValue = wrestleDetails.weight,
                onSelectWeight = onClickWeight
            )
        }

        HorizontalDivider()
    }

}

