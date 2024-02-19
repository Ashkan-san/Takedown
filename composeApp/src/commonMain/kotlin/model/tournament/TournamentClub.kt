package model.tournament

import io.realm.kotlin.types.EmbeddedRealmObject

class TournamentClub : EmbeddedRealmObject {
    var name: String = ""
    var website: String = ""
    var image: String = ""
}