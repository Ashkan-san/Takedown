package ui.tournaments

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.rickclephas.kmm.viewmodel.KMMViewModel
import model.tournament.Ranking
import model.tournament.Tournament
import model.tournament.TurnierLatLng


class TournamentViewModel(
    private val repository: TournamentRepository
) : KMMViewModel() {
    var tournaments = mutableStateListOf<Tournament>()
    var selectedTournament = mutableStateOf<Tournament?>(null)
    var selectedRankings = listOf<Ranking>()

    val isTurniereLoading = mutableStateOf(false)

    // SETTINGS AND SEARCH
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
        tournaments = repository.getAllTournaments().toMutableStateList()
    }

    fun selectTournament(tournament: Tournament) {
        selectedTournament.value = tournament
    }

    /**
     * Alle 1. Plätze eines Turniers zurückgeben
     */
    fun getWinnersByAge(): Map<String, List<Ranking>> {
        return selectedTournament.value!!.rankings
            .filter { it.rank == "1." }
            .groupBy { it.ageClass }
    }

    /**
     * Abhängig vom angeclickten Gewinner die passenden Rankings anzeigen
     */
    fun setRankings(ranking: Ranking) {
        selectedRankings = selectedTournament.value!!.rankings
            .filter {
                it.ageClass == ranking.ageClass && it.weightClass == ranking.weightClass
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

