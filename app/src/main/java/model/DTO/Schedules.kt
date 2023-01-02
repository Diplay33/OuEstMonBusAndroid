package model.DTO

import model.DAO.ScheduleDAO

class Schedules {
    companion object {
        fun getSchedulesByStationAndPathAndDate(
            stationId: String,
            date: String,
            pathId: Int,
            callback: (List<Schedule>) -> Unit
        ) {
            ScheduleDAO.getSchedulesByStationAndPathAndDate(stationId, date, pathId) { schedules ->
                callback(schedules)
            }
        }

        fun getSchedulesByStationAndPathsAndDate(
            stationId: String,
            date: String,
            paths: List<Path>,
            callback: (List<Schedule>) -> Unit
        ) {
            paths.forEach { path ->
                getSchedulesByStationAndPathAndDate(stationId, date, path.id) { schedules ->
                    callback(schedules)
                }
            }
        }
    }
}