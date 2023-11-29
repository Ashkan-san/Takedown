package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Turnier
import data.TurnierDetails
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
            withContext(Dispatchers.IO) { turniere.addAll(fetchAllTurniere()) }
            isLoading.value = false
        }
    }

    // Aktuelles Turnier setzen und Details hinzufügen
    fun populateTurnierDetails(turnier: Turnier) {
        aktuellesTurnier.value = turnier

        viewModelScope.coroutineScope.launch {
            withContext(Dispatchers.IO) {
                aktuellesTurnier.value!!.turnierDetails.addAll(fetchTurnierDetails(turnier))
                aktuellesTurnier.value!!.turnierDetails = aktuellesTurnier.value!!.turnierDetails.distinct().toMutableStateList()
            }
        }
    }

}

expect suspend fun fetchAllTurniere(): MutableList<Turnier>

expect suspend fun fetchTurnierDetails(turnier: Turnier): MutableList<TurnierDetails>