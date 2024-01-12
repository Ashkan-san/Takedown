package model.turnier

data class RingenKlasse(
    val altersKlasse: String,
    val stilart: String,
    val gewichtsKlassen: List<String>,
    val geschlecht: List<String>,
    val jahrgaenge: String,
    val modus: String,
)