package model.tournament

import io.realm.kotlin.types.EmbeddedRealmObject

class TournamentDate : EmbeddedRealmObject {
    var startDay: String = ""
    var endDay: String = ""
    var month: String = ""
    var year: String = ""
}