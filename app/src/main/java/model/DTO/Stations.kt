package model.DTO

import model.DAO.StationDAO

class Stations {
    companion object {
        fun getStationById(stationId: String, callback: (Station) -> Unit) {
            StationDAO.getStationById(stationId) { station ->
                callback(station)
            }
        }

        fun getSortedStationsByLineAndDirection(
            lineId: Int,
            direction: String,
            callback: (List<Station>) -> Unit
        ) {
            StationDAO.getStationsByLineAndDirection(lineId, direction) { stations ->
                callback(stations.sortedBy { it.name })
            }
        }
    }
}