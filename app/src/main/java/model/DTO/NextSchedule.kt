package model.DTO

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NextSchedule(val lineId: Int,
                   val destination: String,
                   val rawTheoricTime: String,
                   val rawEstimatedTime: String,
                   /*val theoricTime: LocalDateTime = LocalDateTime
                       .parse(rawTheoricTime),
                   val estimated_time: LocalDateTime = LocalDateTime
                       .parse(raw_estimated_time),*/
                   val isOnline: Boolean = rawTheoricTime != rawEstimatedTime
) {
    fun getTheoricTimeLeft(): String {
        return getTimeLeft(rawTheoricTime)
    }

    fun getEstimatedTimeLeft(): String {
        return getTimeLeft(rawEstimatedTime)
    }

    private fun getTimeLeft(eventTime: String): String {
        var day = 0
        var hh = 0
        var mm = 0
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val eventDate = dateFormat.parse(eventTime)
            val cDate = Date()
            val timeDiff = eventDate.time - cDate.time

            day = TimeUnit.MILLISECONDS.toDays(timeDiff).toInt()
            hh = (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day.toLong())).toInt()
            mm =
                (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff))).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if(hh == 0 && day == 0) {
            "$mm"
        }
        else if(day == 0) {
            "$hh hour $mm min"
        }
        else {
            "$day days $hh hour $mm min"
        }
    }
}