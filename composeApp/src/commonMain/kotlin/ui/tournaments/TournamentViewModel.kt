package ui.tournaments

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.rickclephas.kmm.viewmodel.KMMViewModel
import model.tournament.Tournament
import model.turnier.Turnier
import model.turnier.TurnierLatLng
import model.turnier.TurnierPlatzierung


class TournamentViewModel : KMMViewModel() {
    // Realm Repo
    val repo = TournamentRepository()

    var turniere = mutableStateListOf<Tournament>()
    var aktuellesTurnier = mutableStateOf<Turnier?>(null)
    var aktuellePlatzierungen = listOf<TurnierPlatzierung>()

    val isTurniereLoading = mutableStateOf(false)

    // Filter Optionen und State
    val showBottomSheet = mutableStateOf(false)
    val filterOptions = listOf("Option 1", "Option 2", "Option 3")
    val selectedOptions = mutableStateMapOf<String, Boolean>()
    val searchQuery = mutableStateOf("")

    val locationState = mutableStateOf<TurnierLatLng?>(TurnierLatLng(0.0, 0.0))
    val isMapLoaded = mutableStateOf(false)

    init {
        // TEST
        getAllTournaments()
    }

    fun getAllTournaments() {
        // TODO brauche ich hier noch mutablestatelist?
        turniere = repo.getAllTournaments().toMutableStateList()
    }

    /*// aktuelle Turniere updaten und neue hinzufügen
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
    }*/

    /*fun updateTurnierListe(turnier: Turnier) {
        // Turnier mit selber ID finden und updaten
        val turnierIndex = turniere.indexOfFirst { it.id == turnier.id }
        turniere[turnierIndex] = turnier
    }*/

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

    fun toggleBottomSheet(boolean: Boolean) {
        showBottomSheet.value = boolean
    }

}

