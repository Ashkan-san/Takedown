package ui.scoreboard.punkte

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PunkteSurface(
    modifier: Modifier,
    color: Color,
    score: Int,
    penaltyScore: Int,
    onClickAdd: () -> Unit,
    onClickSub: () -> Unit,
    onClickPenalty: () -> Unit,
    onClickItem: (Int) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = color,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /*Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PenaltiesVertical(penaltyScore = penaltyScore)
                Text(
                    modifier = Modifier.clickable { onClickAdd() },
                    text = "$score",
                    fontSize = 100.sp,
                    textAlign = TextAlign.Center
                )
            }*/
            Text(
                modifier = Modifier.clickable { onClickAdd() },
                text = "$score",
                fontSize = 100.sp,
                textAlign = TextAlign.Center
            )

            PenaltiesHorizontal(penaltyScore = penaltyScore)

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Haghighi Fashi",
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Ashkan",
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(80.dp))


            PunkteButtons(
                onClickAdd = onClickAdd,
                onClickSub = onClickSub,
                onClickPenalty = onClickPenalty,
                onClickItem = onClickItem
            )

        }

    }
}