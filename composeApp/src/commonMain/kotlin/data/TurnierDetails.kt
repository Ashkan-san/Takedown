package data

// Ein TurnierDetails liegt in einer Liste von TurnierDetails
data class TurnierDetails(
    val altersKlasse: String,
    val stilart: String,
    val gewichtsKlassen: MutableList<String>,
    val geschlecht: MutableList<String>,
    val jahrgaenge: String,
    val modus: String
)