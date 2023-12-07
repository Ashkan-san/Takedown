package data

data class TurnierAlterGewichtKlasse(
    val altersKlasse: String,
    val stilart: String,
    val gewichtsKlassen: MutableList<String>,
    val geschlecht: MutableList<String>,
    val jahrgaenge: String,
    val modus: String,
)