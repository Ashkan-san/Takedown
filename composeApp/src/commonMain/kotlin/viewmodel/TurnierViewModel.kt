package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Turnier
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
        populateViewModel()
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
        /*if (aktuellesTurnier.value == turnier) {
            return
        }*/
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
        }
    }

}

