package model.DTO

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NextSchedule(val lineId: Int,
                   val destination: String,
                   val vehicleId: Int?,
                   val rawTheoricTime: String,
                   val rawEstimatedTime: String,
                   val isOnline: Boolean = vehicleId != null
) {
    private fun getTheoreticalTimeLeft(): String {
        return getTimeLeft(rawTheoricTime)
    }

    private fun getEstimatedTimeLeft(): String {
        return getTimeLeft(rawEstimatedTime)
    }

    fun getTimeLeft(): String {
        return if (isOnline) getEstimatedTimeLeft() else getTheoreticalTimeLeft()
    }

    private fun getTimeLeft(eventTime: String): String {
        var mm = 0
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val eventDate = dateFormat.parse(eventTime)
            val cDate = Date()
            val timeDiff = eventDate.time - cDate.time

            mm = (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff))).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return "$mm"
    }
}