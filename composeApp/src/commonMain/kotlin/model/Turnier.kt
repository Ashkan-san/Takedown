package model

import androidx.compose.runtime.snapshots.SnapshotStateList

// UI State für Turnier, ist immutable
data class Turnier(
    val id: String,
    val titel: String,

    val datum: TurnierDatum,
    val beendet: Boolean,

    val land: String = "",
    val adresse: String = "",
    val stadt: String,

    val veranstalter: String,
    val verein: String,

    // TODO var weg, immutable machen
    var alterGewichtsKlassen: SnapshotStateList<RingenKlasse>,

    val platzierungen: List<TurnierPlatzierung>
)