package data

// UI State für Turnier, ist immutable
data class Turnier(
    val datum: TurnierDatum,
    val titel: String = "",
    // TODO Stadt nehmen und Bundesland herausfinden? Oder iwie Karte einbauen
    val ort: String = "", //TODO zu TurnierOrt ändern
    val veranstalter: String = "",
    val verein: String = "",
    val detailsLink: String = "",
    var turnierDetails: MutableList<TurnierDetails>
)