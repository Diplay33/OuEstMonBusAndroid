package model.DTO

import model.DAO.StationDAO

class Stations {
    companion object {
        fun getStationById(stationId: String, callback: (Station) -> Unit) {
            StationDAO.getStationById(stationId) { station ->
                callback(station)
            }
        }
    }
}