package scraper

import model.turnier.TurnierDatum
import model.turnier.TurnierPlatzierung

fun parseDate(dateString: String): TurnierDatum {
    // 2 Zahlen
    // Optionale Minus mit 2 Zahlen
    // 2 Zahlen
    // 4 Zahlen
    // Beispiel: 04.-05.11.2023
    val regex = """(\d{2})(?:\.-(\d{2}))?\.(\d{2})\.(\d{4})""".toRegex()
    val matchResult = regex.find(dateString) ?: return TurnierDatum("", "", "", "", dateString)
    val (startTag, endTag, monat, jahr) = matchResult.destructured

    return TurnierDatum(startTag, endTag, monat, jahr, dateString)
}

fun parseSiegerList(input: String): MutableList<TurnierPlatzierung> {
    val resultList = mutableListOf<TurnierPlatzierung>()
    var weight = ""
    var age = ""
    val lines = input.dropLast(2).split("\n").drop(4).filter { it.isNotBlank() }

    for (line in lines) {
        val parts = line.split("\t").filter { it.isNotBlank() && !it.contains(Regex("\\b\\d+\\s(?:Teilnehmer|Participants)\\b")) }

        var rank = ""
        var name = ""
        var club = ""

        if (parts.size == 2) {
            weight = parts[0].replace("kg", "")
            age = parts[1]
        }
        if (parts.size == 3) {
            rank = parts[0].replace(".", "")
            name = parts[1]
            club = parts[2].dropLast(1)

            resultList.add(TurnierPlatzierung(gewichtsKlasse = weight, altersKlasse = age, platzierung = rank, ringerName = name, verein = club))
        }
    }

    return resultList
}