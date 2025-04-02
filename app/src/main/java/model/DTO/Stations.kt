package model.DTO

import kotlinx.coroutines.runBlocking
import model.DAO.StationDAO
import java.text.Normalizer

class Stations {
    companion object {
        fun getStationById(stationId: String, network: String, callback: (Station) -> Unit) {
            if(network == "tbm") {
                StationDAO.getStationById(stationId) { callback(it) }
            }
            else {
                callback(StationDAO.getGTFSStationById(stationId, network))
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

        fun getSortedStationsByPaths(network: String, paths: List<Path>, callback: (List<Station>) -> Unit) {
            when(network) {
                "tbm" -> paths.forEach { path ->
                    StationDAO.getStationsByPath(path.id) { returnedStations ->
                        callback(returnedStations)
                    }
                }
                "star" -> callback(StationDAO.getGTFSStationByPaths(paths.map { it.id }, network))
                "" -> callback(listOf())
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