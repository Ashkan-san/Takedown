package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Turnier
import data.TurnierDatum
import fetchAllTurniere
import fetchAlterGewichtKlassen
import fetchDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurnierViewModel : KMMViewModel() {
    // Liste der Turniere
    val turniere = mutableStateListOf<Turnier>()
    val isLoading = mutableStateOf(false)
    var aktuellesTurnier = mutableStateOf<Turnier?>(null)

    init {
        // TODO Nur zum Testen, später ändern
        populateViewModel()
        /*turniere.addAll(
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
                    mutableStateListOf()
                ),
                        Turnier(
                    "23417",
                    "33. Turnier der männlichsten Männer des Männerclubs",
                    TurnierDatum("23", "26", "01", "2023", "23.-26.01.2023"),
                    "Männerland",
                    "Männerweg 2301, 22399 Männerstadt",
                    "Männerstadt",
                    "Mann",
                    "Männerverein",
                    mutableStateListOf()
                ),
                    Turnier(
                    "23417",
                    "33. Turnier der männlichsten Männer des Männerclubs",
                    TurnierDatum("23", "26", "01", "2023", "23.-26.01.2023"),
                    "Männerland",
                    "Männerweg 2301, 22399 Männerstadt",
                    "Männerstadt",
                    "Mann",
                    "Männerverein",
                    mutableStateListOf()
                )
            )
        )*/
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

}

