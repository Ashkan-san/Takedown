package di

import org.koin.dsl.module
import ui.scoreboard.ScoreboardViewModel
import ui.tournaments.TournamentRepository
import ui.tournaments.TournamentViewModel
import viewModelDefinition

fun sharedModule() = module {
    single { TournamentRepository() }

    viewModelDefinition { ScoreboardViewModel() }
    viewModelDefinition { TournamentViewModel(get()) }
}