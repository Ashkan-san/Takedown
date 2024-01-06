package repo

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmExample : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()

    var name: String = ""
    var date: Int = 0
}