package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Turnier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurnierViewModel() : KMMViewModel() {
    // Liste der Turniere
    val turniere = mutableStateListOf<Turnier>()
    val isLoading = mutableStateOf(false)

    init {
        viewModelScope.coroutineScope.launch {
            populateViewModel()
        }
    }

    private suspend fun populateViewModel() {
        isLoading.value = true
        withContext(Dispatchers.IO) { turniere.addAll(fetchAllTurniere()) }
        isLoading.value = false
    }

}

expect suspend fun fetchAllTurniere(): MutableList<Turnier>