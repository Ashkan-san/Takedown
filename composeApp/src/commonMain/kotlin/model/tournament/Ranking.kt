package model.tournament

import io.realm.kotlin.types.EmbeddedRealmObject

class Ranking : EmbeddedRealmObject {
    var weightClass: String = ""
    var ageClass: String = ""
    var rank: String = ""
    var name: String = ""
    var club: String = ""
}