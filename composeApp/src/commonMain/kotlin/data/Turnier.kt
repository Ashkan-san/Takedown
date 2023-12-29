package data

import androidx.compose.runtime.snapshots.SnapshotStateList

// UI State für Turnier, ist immutable
data class Turnier(
    val id: String,
    val titel: String,

    val datum: TurnierDatum,

    val land: String = "",
    val adresse: String = "",
    val stadt: String,

    val veranstalter: String,
    val verein: String,

    var alterGewichtsKlassen: SnapshotStateList<RingenKlasse>,

    var platzierungen: SnapshotStateList<TurnierPlatzierung>
)