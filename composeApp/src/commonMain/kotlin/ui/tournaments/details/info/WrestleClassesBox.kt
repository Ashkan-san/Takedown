package ui.tournaments.details.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.tournament.Tournament
import data.model.tournament.WrestleClass

@Composable
fun WrestleClassesBox(tournament: Tournament) {
    if (tournament.wrestleClasses.isNotEmpty()) {
        /*SectionText("Age-/Weight Classes")
        Spacer(modifier = Modifier.height(10.dp))*/
        WrestleStyle("Freestyle", tournament)
        Spacer(modifier = Modifier.height(10.dp))
        WrestleStyle("Greco-Roman", tournament)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun WrestleStyle(style: String, tournament: Tournament) {
    val sortedClasses = sortClassesInStyles(style, tournament)

    if (sortedClasses.isNotEmpty()) {
        Text(
            text = style,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        sortedClasses.forEachIndexed { index, wrestleClass ->
            WrestleClassContainer(wrestleClass)
            if (index < sortedClasses.lastIndex) HorizontalDivider(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun WrestleClassContainer(wrestleClass: WrestleClass) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(2.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // GENDER
        Box(modifier = Modifier.weight(3F)) {
            Row {
                wrestleClass.genders.forEach {
                    GenderIcon(it)
                }
            }
        }

        // AGECLASS, AGES
        Box(modifier = Modifier.weight(4F)) {
            Column {
                Text(
                    text = wrestleClass.title,
                    fontWeight = FontWeight.Bold,
                    //textAlign = TextAlign.Center,
                    //maxLines = 2
                )
                Text(
                    text = wrestleClass.ages,
                    //textAlign = TextAlign.Center,
                )
            }
        }

        Spacer(Modifier.width(5.dp))

        // STYLE
        Box(modifier = Modifier.weight(4F)) {
            // WEIGHTCLASSES
            Row {
                Text(
                    text = wrestleClass.weightClasses.joinToString(", ")
                )
            }
        }
    }
}

@Composable
fun GenderIcon(gender: String) {
    when (gender) {
        "Männlich" -> Icon(imageVector = Icons.Default.Male, contentDescription = "Male Icon", modifier = Modifier.size(40.dp))
        "Weiblich" -> Icon(imageVector = Icons.Default.Female, contentDescription = "Female Icon", modifier = Modifier.size(40.dp))
    }
}

// TODO prob woanders hin
/**
 * Nur die WrestleClasses eines bestimmten Styles zurückgeben
 */
private fun sortClassesInStyles(style: String, tournament: Tournament): List<WrestleClass> {
    if (style == "Freestyle") return tournament.wrestleClasses.filter { it.wrestleStyle == "Freistil" }
    return tournament.wrestleClasses.filter { it.wrestleStyle == "Gr.-röm." }
}