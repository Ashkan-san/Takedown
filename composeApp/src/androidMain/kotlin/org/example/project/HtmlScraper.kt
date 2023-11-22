package org.example.project

import android.content.ContentValues
import android.util.Log
import data.Turnier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.htmlunit.BrowserVersion
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlDivision
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTableBody
import org.htmlunit.html.HtmlTableCell
import org.htmlunit.html.HtmlTableRow

suspend fun fetchAllTurniere(): MutableList<Turnier> {
    val browser = WebClient(BrowserVersion.CHROME)
    browser.options.isThrowExceptionOnScriptError = false
    browser.options.isCssEnabled = false
    browser.javaScriptTimeout = 30000

    var turnierListe = mutableListOf<Turnier>()

    try {
        val website: HtmlPage = withContext(Dispatchers.IO) {
            browser.getPage("https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=2023&land=DE&ZB=E")
        }
        // Erster Fetch
        turnierListe = fetchTable(website)

        var nextPageInput = website.getFirstByXPath<HtmlSubmitInput>("//input[@class='rgPageNext']")
        val pagesDiv = website.getFirstByXPath<HtmlDivision>("//div[@class='rgWrap rgNumPart']")
        val pageCount = pagesDiv.childElementCount

        // Solange es noch weitere Seiten gibt, weitere Fetches
        repeat(pageCount) {
            if (nextPageInput != null) {
                val nextPage = nextPageInput.click<HtmlPage>()

                //browser.waitForBackgroundJavaScript(1000)
                // Seite lesen und in Liste packen
                val table = fetchTable(nextPage)
                turnierListe.addAll(table)

                // Page updaten
                nextPageInput = nextPage.getFirstByXPath("//input[@class='rgPageNext']")
            }
        }

    } catch (e: Exception) {
        Log.d(ContentValues.TAG, "Die Exception ist: $e")
    }

    //browser.close()
    return turnierListe
}

private fun fetchTable(nextPage: HtmlPage): MutableList<Turnier> {
    val temporaryList = mutableListOf<Turnier>()
    // Table finden und Rows in Liste, dann über Rows iterieren
    val tableBody = nextPage.getFirstByXPath<HtmlTableBody>("//table[@class='rgMasterTable']/tbody")
    val rows: List<HtmlTableRow> = tableBody.rows

    rows.forEach { row ->
        val cells: List<HtmlTableCell> = row.cells

        val datum = cells[0].asNormalizedText()
        val titel = cells[1].asNormalizedText()
        val ort = cells[2].asNormalizedText()
        val veranstalter = cells[3].asNormalizedText()
        val verein = cells[4].asNormalizedText()
        val details = cells[5].asNormalizedText()

        val turnier = Turnier(
            datum = datum,
            titel = titel,
            ort = ort,
            veranstalter = veranstalter,
            verein = verein,
            details = details
        )

        temporaryList.add((turnier))
        //println("Datum: $datum Titel: $titel Ort: $ort Veranstalter: $veranstalter Verein: $verein Details: $details")
    }
    return temporaryList
}