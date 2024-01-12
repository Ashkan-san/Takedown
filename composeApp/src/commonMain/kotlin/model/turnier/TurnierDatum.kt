package model.turnier

data class TurnierDatum(
    val startTag: String,
    val endTag: String,
    val monat: String,
    val jahr: String,

    val datumString: String
)