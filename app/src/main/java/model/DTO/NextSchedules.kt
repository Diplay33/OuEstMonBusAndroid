package model.DTO

import model.DAO.NextScheduleDAO

class NextSchedules {
    companion object {
        fun getNextSchedulesByStationId(stationId: String, callback: (List<NextSchedule>) -> Unit) {
            NextScheduleDAO.getNextSchedulesByStationId(stationId) { nextSchedules ->
                callback(nextSchedules)
            }
        }

        //fun get
    }
}