package data.model.tournament

import io.realm.kotlin.types.EmbeddedRealmObject

class TournamentDate : EmbeddedRealmObject {
    var startDay: String = ""
    var endDay: String = ""
    var month: String = ""
    var year: String = ""

    override fun toString(): String {
        if (endDay.isNotEmpty()) {
            return "${startDay}.-${endDay}.${month}.${year}"
        }
        return "${startDay}.${month}.${year}"
    }

    fun getAbbreviatedMonth(): String {
        return when (month) {
            "01" -> "JAN"
            "02" -> "FEB"
            "03" -> "MAR"
            "04" -> "APR"
            "05" -> "MAY"
            "06" -> "JUN"
            "07" -> "JUL"
            "08" -> "AUG"
            "09" -> "SEP"
            "10" -> "OCT"
            "11" -> "NOV"
            "12" -> "DEC"
            else -> ""
        }
    }
}