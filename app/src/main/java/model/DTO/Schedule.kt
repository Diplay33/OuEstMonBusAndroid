package model.DTO

import java.text.SimpleDateFormat
import java.util.*

class Schedule(val pathId: Int,
               val rawAppTime: String,
               val rawRealTime: String?,
               val state: String
) {
    fun getTime(): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return if (rawAppTime == "") null else dateFormat.parse(rawAppTime)
    }
}