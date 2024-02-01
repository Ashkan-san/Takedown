package ui.scoreboard.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.scoreboard.WrestleDetailsState
import model.scoreboard.WrestleStyle

@Composable
fun Info(
    style: WrestleStyle,
    styles: List<WrestleStyle>,
    periods: List<Int>,
    wrestleDetails: WrestleDetailsState,
    onClickStyle: (WrestleStyle) -> Unit,
    onClickPeriod: (Int) -> Unit,
    onClickWeight: (Int) -> Unit,
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
            // TODO eine einzelne Methode statt die drei
            StyleDropdown(
                modifier = Modifier.weight(1f),
                styles = styles,
                text = wrestleDetails.style,
                onClickItem = onClickStyle
            )
            PeriodDropdown(
                modifier = Modifier,
                periods = periods,
                text = wrestleDetails.period,
                onClickItem = onClickPeriod
            )
            WeightDropdown(
                modifier = Modifier.weight(1f),
                weights = style.weightClasses,
                text = wrestleDetails.weight,
                onClickItem = onClickWeight
            )
        }

        Divider()
    }

}

