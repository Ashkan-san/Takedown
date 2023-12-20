package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Turnier
import data.TurnierDatum
import data.TurnierPlatzierung
import fetchAllTurniere
import fetchAlterGewichtKlassen
import fetchDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurnierViewModel : KMMViewModel() {
    val turniere = mutableStateListOf<Turnier>()
    val isLoading = mutableStateOf(false)
    var aktuellesTurnier = mutableStateOf<Turnier?>(null)

    // TODO später ändern, iwie mit callback und so
    var aktuellePlatzierungen = listOf<TurnierPlatzierung>()

    //var lat = mutableStateOf(0.0)
    //var lng = mutableStateOf(0.0)

    init {
        // TODO Nur zum Testen, später ändern
        //populateViewModel()
        turniere.addAll(
            listOf(
                Turnier(
                    "23417",
                    "33. Turnier der männlichsten Männer des Männerclubs",
                    TurnierDatum("23", "26", "01", "2023", "23.-26.01.2023"),
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
        )
    }

    fun populateViewModel() {
        viewModelScope.coroutineScope.launch {
            isLoading.value = true
            withContext(Dispatchers.IO) {
                turniere.addAll(fetchAllTurniere())
            }
            isLoading.value = false
        }
    }

    fun populateTurnierDetails(turnier: Turnier) {
        // Gewähltes Turnier setzen
        aktuellesTurnier.value = turnier

        viewModelScope.coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Turnier Details hinzufügen
                aktuellesTurnier.value = fetchDetails(turnier)

                // Turnier A/G-Klassen
                // Nur updaten, wenn neues dabei
                val fetchedAGKlassen = fetchAlterGewichtKlassen(turnier)
                val filteredAGKlassen = fetchedAGKlassen.filter { ag ->
                    aktuellesTurnier.value!!.alterGewichtsKlassen.none { it == ag }
                }

                aktuellesTurnier.value!!.alterGewichtsKlassen.addAll(filteredAGKlassen)
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

}

