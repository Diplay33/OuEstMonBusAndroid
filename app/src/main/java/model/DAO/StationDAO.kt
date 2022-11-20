package model.DAO

import model.DAO.AccessData.CallAPI
import model.DTO.Station
import org.json.JSONObject

class StationDAO {
    companion object {
        fun getStationById(stationId: String, callback: (Station) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_arret_p?key=0234ABEFGH&filter={\"GID\":$stationId}") { responseBody ->
                try {
                    var station = Station(0, "Arrêt inconnu",  0.0, 0.0)
                    val welcomeJSONObject = JSONObject(responseBody)
                    val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                    try {
                        for(i in 0 until featuresJSONArray.length()) {
                            val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                            val geometry = if (featuresJSONObject.isNull("geometry"))
                                null
                            else
                                featuresJSONObject.getJSONObject("geometry")
                            val latitude = geometry?.getJSONArray("coordinates")?.getDouble(1) ?: 0.0
                            val longitude = geometry?.getJSONArray("coordinates")?.getDouble(0) ?: 0.0
                            val name = featuresJSONObject.getJSONObject("properties").getString("libelle")

                            station = Station(stationId.toInt(), name, latitude, longitude)
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process : $e")
                    }

                    callback(station)
                }
                catch(e: Exception) {
                    println("Error during decoding process : $e")
                }
            }

        }
    }
}