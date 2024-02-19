package model.tournament

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class Tournament : RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var id: Int = 0
    var name: String = ""
    var date: TournamentDate? = TournamentDate()
    var status: String = ""

    var country: String = ""
    var city: String = ""
    var venue: String = ""
    var host: String = ""
    var club: TournamentClub? = TournamentClub()
    var wrestlerCount: Int = 0

    var wrestleClasses: RealmList<WrestleClass> = realmListOf()
    var rankings: RealmList<Ranking> = realmListOf()
}