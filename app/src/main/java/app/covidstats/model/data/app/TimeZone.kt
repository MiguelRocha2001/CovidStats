package app.covidstats.model.data.app

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TimeZone(val day: Int = Calendar.DAY_OF_MONTH, val hour: Int = Calendar.HOUR_OF_DAY) {
    fun timeExpired(): Boolean {
        val currentDay = Calendar.DAY_OF_MONTH
        val currentHour = Calendar.HOUR_OF_DAY
        if (currentDay != day || currentHour > hour + 6) {
            return true
        }
        return false
    }
}
