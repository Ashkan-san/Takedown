package scraper

import org.htmlunit.BrowserVersion
import org.htmlunit.WebClient

// Web Client Setup
fun setupWebClient(): WebClient {
    val webClient = WebClient(BrowserVersion.CHROME)
    webClient.options.isThrowExceptionOnScriptError = false
    webClient.options.isCssEnabled = false
    webClient.javaScriptTimeout = 120000

    return webClient
}

// Wenn X im Feld vorhanden, jeweiliges Geschlecht in Liste einfügen
fun determineGender(male: String, female: String): List<String> {
    val genderList = mutableListOf<String>()

    if (male == "X") {
        genderList.add("Männlich")
    } else if (female == "X") {
        genderList.add("Weiblich")
    } else {
        return genderList
    }

    return genderList
}

// Gewichtsklassenstring in einzelne Strings splitten und Liste
fun determineWeightclass(weightclassesString: String): List<String> {
    val weightClassesList = weightclassesString.split(", ")
    return weightClassesList
}