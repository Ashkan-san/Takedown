package de.takedown

import model.tournament.TournamentDate
import org.htmlunit.BrowserVersion
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlTableRow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.system.exitProcess

fun getTournamentCountry(link: String): String {
    val pattern = Regex("""(?<=/([^/]+/){6})\w+(?=/)""")

    val matchResult = pattern.find(link)
    val countryCode = matchResult?.value ?: ""

    return countryCode
}

/**
 * Einen Link aus den jeweiligen Variablen Jahr, Land und Kommende/Ergebnisse erstellen
 */
fun getTournamentLink(year: String, country: String, pastUp: String): String {
    return "https://www.ringerdb.de/de/Turniere/Turnieruebersicht.aspx?Saison=${year}&land=${country}&ZB=${pastUp}"
}

/**
 * Prüfen, ob der Table überhaupt Daten enthält
 */
fun checkTableEmpty(row: HtmlTableRow): Boolean {
    return row.getAttribute("class").contains("rgNoRecords")
}

/**
 * Die ID aus dem Link extrahieren
 */
fun getTournamentId(link: String): Int {
    val pattern = Pattern.compile("TID=(\\d+)")
    val matcher: Matcher = pattern.matcher(link)

    val id: String = if (matcher.find()) {
        matcher.group(1)
    } else {
        ""
    }

    return id.toIntOrNull() ?: 0
}

/**
 * Datumstring in eigenes Turnierdatum parsen
 */
fun parseDate(dateString: String): TournamentDate {
    // 2 Zahlen
    // Optionale Minus mit 2 Zahlen
    // 2 Zahlen
    // 4 Zahlen
    // Beispiel: 04.-05.11.2023
    val regex = """(\d{2})(?:\.-(\d{2}))?\.(\d{2})\.(\d{4})""".toRegex()
    val matchResult = regex.find(dateString) ?: return TournamentDate()
    val (startDay, endDay, month, year) = matchResult.destructured

    val date = TournamentDate().apply {
        this.startDay = startDay
        this.endDay = endDay
        this.month = month
        this.year = year
    }
    return date
}

/**
 * Wenn aktuelles Datum nach Turnierdatum ist, ist Turnier beendet
 */
fun setTournamentStatus(date: TournamentDate): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY)
    val currentDate = Date()
    val turnierDate = dateFormat.parse("${date.startDay}.${date.month}.${date.year}")
    var status = ""

    if (currentDate.after(turnierDate)) status = "FINISHED"
    else if (currentDate.before(turnierDate)) status = "UPCOMING"
    else if (currentDate.equals(turnierDate)) status = "RUNNING"

    return status
}

/**
 * Den langen String der Gewichtsklassen in einzelne splitten
 */
fun getWeightclasses(string: String): List<String> {
    return string.split(", ")//.map { it.toInt() }
}

/**
 * Jenach Kreuz ein Geschlecht hinzufügen
 */
fun determineGenders(male: String, female: String): Set<String> {
    val genders = mutableSetOf<String>()

    if (male == "X") {
        genders.add("Männlich")
    } else if (female == "X") {
        genders.add("Weiblich")
    }

    return genders
}

fun debugExit() {
    if (breakBoolean) exitProcess(-1)
}

/**
 * Den HTTPUnit WebClient aufsetzen
 */
fun setupWebClient(): WebClient {
    val webClient = WebClient(BrowserVersion.CHROME)
    webClient.options.isThrowExceptionOnScriptError = false
    webClient.options.isCssEnabled = false
    webClient.javaScriptTimeout = 120000

    /*webClient.cssErrorHandler = SilentCssErrorHandler()
    webClient.javaScriptErrorListener = SilentJavaScriptErrorListener()
    webClient.htmlParserListener = HTMLParserListener.LOG_REPORTER

    System.getProperties().setProperty("org.apache.commons.logging.simplelog.defaultlog", "error")
    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog")
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").level = Level.OFF
    java.util.logging.Logger.getLogger("com.gargoylesoftware").level = Level.OFF
    java.util.logging.Logger.getLogger("org.htmlunit.html").level = Level.OFF
    java.util.logging.Logger.getLogger("org.apache.http.wire").level = Level.OFF
    java.util.logging.Logger.getLogger("org.apache.commons.httpclient").level = Level.OFF
    java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").level = Level.OFF
    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.ActiveXObject").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.html.HtmlScript").setLevel(Level.OFF);
    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.WindowProxy").setLevel(Level.OFF)
    java.util.logging.Logger.getLogger("org.apache").setLevel(Level.OFF)*/
    return webClient
}