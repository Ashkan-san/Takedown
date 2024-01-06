package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.MyLatLng
import data.RingenKlasse
import data.Turnier
import data.TurnierDatum
import data.TurnierPlatzierung
import fetchAllTurniere
import fetchDetails
import fetchRingenKlassen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import repo.ExampleRepo

class TurnierViewModel : KMMViewModel() {
    var turniere = mutableStateListOf<Turnier>()
    val isLoading = mutableStateOf(false)
    var aktuellesTurnier = mutableStateOf<Turnier?>(null)
    var aktuellePlatzierungen = listOf<TurnierPlatzierung>()

    // Filter Optionen und State
    val filterOptions = listOf("Option 1", "Option 2", "Option 3")
    val selectedOptions = mutableStateMapOf<String, Boolean>()
    val searchQuery = mutableStateOf("")

    val locationState = mutableStateOf<MyLatLng?>(MyLatLng(0.0, 0.0))
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
                        TurnierPlatzierung("80", "U17", "2", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "3", "Ashkan Haghighi Fashi", "TSV Wandsetal"),
                        TurnierPlatzierung("80", "U17", "4", "Ashkan Haghighi Fashi", "TSV Wandsetal")
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
            isLoading.value = true
            withContext(Dispatchers.IO) {
                val fetchedTurniere = fetchAllTurniere()

                withContext(Dispatchers.Main) {
                    turniere.addAll(fetchedTurniere)
                    turniere = turniere.distinctBy { it.id }.toMutableStateList()
                }
            }
            isLoading.value = false
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

            updateTurnier(aktuellesTurnier.value!!)
        }
    }

    fun updateTurnier(turnier: Turnier) {
        // Turnier mit selber ID finden und updaten
        val turnierIndex = turniere.indexOfFirst { it.id == turnier.id }
        turniere[turnierIndex] = turnier
    }

    fun updatePlatzierungen(platzierungen: List<TurnierPlatzierung>) {
        aktuellePlatzierungen = platzierungen
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        locationState.value = MyLatLng(latitude, longitude)
    }

    fun setMapLoaded() {
        isMapLoaded.value = true
    }

    fun addExample() {
        viewModelScope.coroutineScope.launch {
            repo.addExample()
        }
    }

}

