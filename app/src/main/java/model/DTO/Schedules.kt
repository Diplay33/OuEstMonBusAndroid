package model.DTO

import android.icu.util.Calendar
import model.DAO.ScheduleDAO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Schedules {
    companion object {
        private fun getSchedulesByStationAndDate(
            stationId: String,
            date: String,
            callback: (List<Schedule>) -> Unit
        ) {
            ScheduleDAO.getSchedulesByStationAndPathAndDate(stationId, date) { schedules ->
                callback(schedules)
            }
        }

        fun getSchedulesByStationAndPaths(
            stationId: String,
            paths: List<Path>,
            callback: (List<Schedule>) -> Unit
        ) {
            val localDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            getSchedulesByStationAndDate(stationId, formatter.format(localDate)) { schedules ->
                callback(schedules.filter { value -> paths.map { it.id }.contains(value.pathId) })

                getSchedulesByStationAndDate(stationId, formatter.format(localDate.plusDays(1))) { values ->
                    callback(values.filter { value -> paths.map { it.id }.contains(value.pathId) })
                }
            }
        }

        fun sortSchedulesByHour(schedules: List<Schedule>): List<List<Schedule>> {
            val sortedSchedules: MutableList<List<Schedule>> = mutableListOf()

            var tempSchedules: MutableList<Schedule> = mutableListOf()
            var lastSchedule = Schedule(0, "2023-01-02T06:00:04", "", "")

            schedules.sortedBy { it.getTime() }.forEach { schedule ->
                if(schedule.getTime() != null) {
                    val lastScheduleCalendar = Calendar.getInstance()
                    val scheduleCalendar = Calendar.getInstance()
                    lastScheduleCalendar.time = lastSchedule.getTime()
                    scheduleCalendar.time = schedule.getTime()

                    lastSchedule = if(lastScheduleCalendar.get(Calendar.HOUR_OF_DAY) == scheduleCalendar.get(Calendar.HOUR_OF_DAY)) {
                        tempSchedules.add(schedule)
                        schedule
                    } else {
                        sortedSchedules.add(tempSchedules)
                        tempSchedules = mutableListOf()
                        tempSchedules.add(schedule)
                        schedule
                    }
                }
            }

            sortedSchedules.add(tempSchedules)
            return sortedSchedules
        }
    }
}