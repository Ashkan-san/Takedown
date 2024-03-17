package data.model.tournament

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmSet

class WrestleClass : EmbeddedRealmObject {
    var title: String = ""
    var ages: String = ""
    var wrestleStyle: String = ""
    var bracketMode: String = ""

    var weightClasses: RealmList<String> = realmListOf()
    var genders: RealmSet<String> = realmSetOf()
}