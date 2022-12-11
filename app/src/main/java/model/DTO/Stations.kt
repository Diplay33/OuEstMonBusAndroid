package model.DTO

import model.DAO.StationDAO
import java.text.Normalizer

class Stations {
    companion object {
        fun getStationById(stationId: String, callback: (Station) -> Unit) {
            StationDAO.getStationById(stationId) { station ->
                callback(station)
            }
        }

        fun getStationByStationId(stationId: String, callback: (Station) -> Unit) {
            StationDAO.getStationByStationId(stationId) { station ->
                callback(station)
            }
        }

        /*fun getSortedStationsByLineAndDirection(
            lineId: Int,
            direction: String,
            callback: (List<Station>) -> Unit
        ) {
            StationDAO.getStationsByLineAndDirection(lineId, direction) { stations ->
                callback(stations.sortedBy { it.name })
            }
        }*/

        fun getSortedStationsByPaths(paths: List<Path>, callback: (List<Station>) -> Unit) {
            paths.forEach { path ->
                StationDAO.getStationsByPath(path.id) { returnedStations ->
                    callback(returnedStations)
                }
            }
        }

        fun filterStationsBySearchText(stations: List<Station>, searchText: String): List<Station> {
            val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
            fun CharSequence.unaccent(): String {
                val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                return REGEX_UNACCENT.replace(temp, "")
            }

            return stations.filter { station ->
                station.name.lowercase().trim().unaccent().contains(
                    searchText.lowercase().trim().unaccent()
                )
            }
        }
    }
}