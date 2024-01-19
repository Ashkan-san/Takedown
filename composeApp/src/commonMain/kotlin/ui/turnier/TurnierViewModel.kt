package ui.turnier

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import fetchAllTurniere
import fetchDetails
import fetchRingenKlassen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.turnier.RingenKlasse
import model.turnier.Turnier
import model.turnier.TurnierDatum
import model.turnier.TurnierLatLng
import model.turnier.TurnierPlatzierung
import repo.ExampleRepo

class TurnierViewModel : KMMViewModel() {
    var turniere = mutableStateListOf<Turnier>()
    val isTurniereLoading = mutableStateOf(false)
    var aktuellesTurnier = mutableStateOf<Turnier?>(null)
    var aktuellePlatzierungen = listOf<TurnierPlatzierung>()

    // Filter Optionen und State
    val showBottomSheet = mutableStateOf(false)
    val filterOptions = listOf("Option 1", "Option 2", "Option 3")
    val selectedOptions = mutableStateMapOf<String, Boolean>()
    val searchQuery = mutableStateOf("")

    val locationState = mutableStateOf<TurnierLatLng?>(TurnierLatLng(0.0, 0.0))
    val isMapLoaded = mutableStateOf(false)

    // Realm Repo
    val repo = ExampleRepo()

    init {
        // TODO Nur zum Testen, später ändern
        //updateTurnierList()
        repeat(5) {
            turniere.add(
                Turnier(
                    "23000 + $it",
                    "33. Turnier der männlichsten Männer des Männerclubs",
                    TurnierDatum("23", "", "01", "2023", "23.01.2023"),
                    true,
                    "Männerland",
                    "Männerweg 2301, 22399 Männerstadt",
                    "Männerstadt",
                    "Mann",
                    "Männerverein",
                    mutableStateListOf(
                        RingenKlasse(
                            "Adultsjjjjjjjjjjjjjjjjjjjjjjjj",
                            "Freistil",
                            gewichtsKlassen = listOf("Freie Einteilungkkkkkkkkkkkkk"),
                            geschlecht = listOf("Männlich", "Weiblich"),
                            jahrgaenge = "U2hhhhhhhhhhhhhhhhh5",
                            modus = "Pool"
                        ),
                        RingenKlasse(
                            "Elderly",
                            "Freistil",
                            listOf("30, 45, 60, 120, 160"),
                            geschlecht = listOf("Weiblich"),
                            jahrgaenge = "U520",
                            modus = "Pool"
                        ),
                        //RingenKlasse("Kids", "Gr.-röm.", listOf("Freie Einteilung"), geschlecht = listOf("Männlich"), jahrgaenge = "2012", modus = "Pool"),
                    ),
                    mutableStateListOf(
                        TurnierPlatzierung("80", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("90", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("100", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("120", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U18", "2", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U19", "3", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "Erwachsene Männer", "4", "Ashkan Haghighi Fashi", "TSV Wandsetal")
                    )
                )
            )
        }
        turniere.add(
            Turnier(
                "23009",
                "33. Turnier der männlichsten Männer des Männerclubs",
                TurnierDatum("01", "", "01", "2024", "01.01.2024"),
                false,
                "Männerland",
                "Männerweg 2301, 22399 Männerstadt",
                "Männerstadt",
                "Mann",
                "Männerverein",
                mutableStateListOf(
                    RingenKlasse(
                        "Adultsjjjjjjjjjjjjjjjjjjjjjjjj",
                        "Freistil",
                        gewichtsKlassen = listOf("Freie Einteilungkkkkkkkkkkkkk"),
                        geschlecht = listOf("Männlich", "Weiblich"),
                        jahrgaenge = "U2hhhhhhhhhhhhhhhhh5",
                        modus = "Pool"
                    ),
                    RingenKlasse(
                        "Elderly",
                        "Freistil",
                        listOf("30, 45, 60, 120, 160"),
                        geschlecht = listOf("Weiblich"),
                        jahrgaenge = "U520",
                        modus = "Pool"
                    ),
                    //RingenKlasse("Kids", "Gr.-röm.", listOf("Freie Einteilung"), geschlecht = listOf("Männlich"), jahrgaenge = "2012", modus = "Pool"),
                ),
                mutableStateListOf(
                    TurnierPlatzierung("80", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                    TurnierPlatzierung("80", "U17", "2", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                    TurnierPlatzierung("80", "U17", "3", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                    TurnierPlatzierung("80", "U17", "4", "Ashkan Haghighi Fashi", "TSV Wandsetal")
                )
            )
        )
        /*turniere.addAll(
            listOf(
                Turnier(
                    "23505",
                    "33. Turnier der männlichsten Männer des Männerclubs",
                    TurnierDatum("23", "26", "01", "2023", "23.-26.01.2023"),
                    "Männerland",
                    "Männerweg 2301, 22399 Männerstadt",
                    "Männerstadt",
                    "Mann",
                    "Männerverein",
                    mutableStateListOf(
                        //TurnierAlterGewichtKlasse("Adults", "Freistil", listOf("Freie Einteilung"))
                    ),
                    mutableStateListOf(
                        TurnierPlatzierung("80", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "2", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "3", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "4", "Ashkan Haghighi Fashi", "TSV Wandsetal")
                    )
                ),
                Turnier(
                    "23506",
                    "Turnier des Männerclubs",
                    TurnierDatum("23", "", "01", "2023", "23.-26.01.2023"),
                    "Männerland",
                    "Männerweg 2301, 22399 Männerstadt",
                    "Männerstadt",
                    "Mann",
                    "Männerverein",
                    mutableStateListOf(),
                    mutableStateListOf(
                        TurnierPlatzierung("80", "U17", "1", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "2", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "3", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "4", "Ashkan Haghighi Fashi", "TSV Wandsetal")
                    )
                ),
            )
        )*/
    }

    // aktuelle Turniere updaten und neue hinzufügen
    fun updateTurnierList() {
        viewModelScope.coroutineScope.launch {
            isTurniereLoading.value = true
            withContext(Dispatchers.IO) {
                val fetchedTurniere = fetchAllTurniere()

                withContext(Dispatchers.Main) {
                    turniere.addAll(fetchedTurniere)
                    turniere = turniere.distinctBy { it.id }.toMutableStateList()
                }
            }
            isTurniereLoading.value = false
        }
    }

    fun updateTurnierDetails(turnier: Turnier) {
        // Gewähltes Turnier setzen
        aktuellesTurnier.value = turnier

        viewModelScope.coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Turnier Details hinzufügen
                val fetchedDetails = fetchDetails(turnier)
                // Turnier A/G-Klassen
                // Nur updaten, wenn neues dabei
                val fetchedRingenKlassen = fetchRingenKlassen(turnier)

                withContext(Dispatchers.Main) {
                    aktuellesTurnier.value = fetchedDetails

                    val filteredRingenKlassen = fetchedRingenKlassen.filter { klasse ->
                        aktuellesTurnier.value!!.alterGewichtsKlassen.none { it == klasse }
                    }

                    aktuellesTurnier.value!!.alterGewichtsKlassen.addAll(filteredRingenKlassen)
                }
            }

            updateTurnierListe(aktuellesTurnier.value!!)
        }
    }

    fun updateTurnierKlassen() {

    }

    fun updateTurnierListe(turnier: Turnier) {
        // Turnier mit selber ID finden und updaten
        val turnierIndex = turniere.indexOfFirst { it.id == turnier.id }
        turniere[turnierIndex] = turnier
    }

    fun getWinners(): Map<String, List<TurnierPlatzierung>> {
        val altersGruppen = groupByAge(aktuellesTurnier.value!!.platzierungen)
        val winnerMap = mutableMapOf<String, List<TurnierPlatzierung>>()

        altersGruppen.forEach { (alter, platzierungen) ->
            val gewichtsGruppen = groupByWeight(platzierungen)
            val sieger = gewichtsGruppen.mapValues { (_, value) -> value.first() }
            winnerMap[alter] = sieger.values.toList()
        }

        return winnerMap
    }

    fun groupByAge(platzierungen: List<TurnierPlatzierung>): Map<String, List<TurnierPlatzierung>> {
        // 1. Alle Ringer einer Altersklasse kriegen
        return platzierungen.groupBy { it.altersKlasse }
    }

    fun groupByWeight(platzierungen: List<TurnierPlatzierung>): Map<String, List<TurnierPlatzierung>> {
        // 2. Alle Ringer einer Gewichtsklasse kriegen
        return platzierungen.groupBy { it.gewichtsKlasse }
    }

    // Nur die Platzierungen der jeweiligen Alters und Gewichtsklasse zeigen
    fun updatePlatzierungen(gewinner: TurnierPlatzierung) {
        aktuellePlatzierungen = aktuellesTurnier.value!!.platzierungen.filter {
            it.altersKlasse == gewinner.altersKlasse && it.gewichtsKlasse == gewinner.gewichtsKlasse
        }
    }


    fun updateLocation(latitude: Double, longitude: Double) {
        locationState.value = TurnierLatLng(latitude, longitude)
    }

    fun setMapLoaded() {
        isMapLoaded.value = true
    }

    fun addExample() {
        viewModelScope.coroutineScope.launch {
            repo.addExample()
        }
    }

    fun toggleBottomSheet(boolean: Boolean) {
        showBottomSheet.value = boolean
    }

}

