package ui


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.select.Elements
import data.Turnier
import moe.tlaster.precompose.navigation.Navigator
import ui.scaffold.MyBottomBar
import ui.scaffold.MyTopBar
import ui.turnier.TurnierScreen

@Composable
fun HomeScreen(navigator: Navigator) {
    Scaffold(
        topBar = {
            MyTopBar()
        },
        bottomBar = {
            MyBottomBar(navigator = navigator)
        },
    ) { innerPadding ->
        TurnierScreen(innerPadding, fetch())
    }
}

private fun fetch(): List<Turnier> {
    // 2023
    val ergebnisse = "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=2023&land=DE&ZB=E"
    val kommende = "https://www.ringerdb.de/de/turniere/Turnieruebersicht.aspx?Saison=2023&land=DE&ZB=K"

    val doc: Document =
        Ksoup.connect(url = ergebnisse)

    val turnierList = mutableListOf<Turnier>()
    // Elements ist MutableList
    // select ist eine css Query, #mp-itn ist class id, b bold und a link
    val turniereTable: Elements = doc.select("table")
    val turnierRows: Elements = turniereTable.select("tr.rgRow, tr.rgAltRow")

    println("title: ${doc.title()}")
    println("Table size: ${turniereTable.size}")
    println("Rows size: ${turnierRows.size}")

    turnierRows.forEach { row: Element ->
        val cells = row.select("td")
        println("Data size: ${cells.size}")

        val datum = cells[0].text()
        val titel = cells[1].text()
        val ort = cells[2].text()
        val veranstalter = cells[3].text()
        val verein = cells[4].text()
        val details = cells[5].text()

        val turnier = Turnier(
            datum = datum,
            titel = titel,
            ort = ort,
            veranstalter = veranstalter,
            verein = verein,
            details = details
        )

        turnierList.add((turnier))
        println("Datum: $datum Titel: $titel Ort: $ort Veranstalter: $veranstalter Verein: $verein Details: $details")
    }

    println(turnierList.toString())
    return turnierList
}

// TODO später eigenes Scaffold
@Composable
fun MyScaffold() {

}