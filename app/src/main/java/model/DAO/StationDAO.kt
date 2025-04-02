package model.DAO

import com.diplay.ouestmonbus.MainApplication
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.runBlocking
import model.CallAPI
import model.DTO.Station
import org.json.JSONArray
import org.json.JSONObject

class StationDAO {
    companion object {
        private val supabase = MainApplication.gtfsSupabase

        fun getStationById(stationId: String, callback: (Station) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_arret_p?key=0234ABEFGH&filter={\"GID\":$stationId}") { responseBody ->
                try {
                    var station = Station("", "", "Arrêt inconnu",  0.0, 0.0)
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
                            val ident = featuresJSONObject.getJSONObject("properties").getString("ident")

                            station = Station(stationId, ident, name, latitude, longitude)
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

        fun getStationByStationId(stationId: String, callback: (Station) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_arret_p?key=0234ABEFGH&filter={\"ident\":\"$stationId\"}") { responseBody ->
                try {
                    var station = Station("", "", "Arrêt inconnu",  0.0, 0.0)
                    val welcomeJSONObject = JSONObject(responseBody)
                    val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                    try {
                        for(i in 0 until featuresJSONArray.length()) {
                            val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                            val geometry = if (featuresJSONObject.isNull("geometry"))
                                null
                            else
                                featuresJSONObject.getJSONObject("geometry")
                            val id = featuresJSONObject.getJSONObject("properties").getString("gid")
                            val latitude = geometry?.getJSONArray("coordinates")?.getDouble(1) ?: 0.0
                            val longitude = geometry?.getJSONArray("coordinates")?.getDouble(0) ?: 0.0
                            val name = featuresJSONObject.getJSONObject("properties").getString("libelle")

                            station = Station(id, stationId, name, latitude, longitude)
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

        /*fun getStationsByLineAndDirection(lineId: Int, direction: String, callback: (List<Station>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/process/saeiv_arrets_sens?key=0234ABEFGH&datainputs={\"gid\":$lineId,\"sens\":\"$direction\"}") { responseBody ->
                try {
                    val stations: MutableList<Station> = mutableListOf()
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
                            val id = featuresJSONObject.getJSONObject("properties").getInt("gid")
                            val name = featuresJSONObject.getJSONObject("properties").getString("libelle")
                            val stationId = featuresJSONObject.getJSONObject("properties").getString("ident")

                            stations.add(Station(id, stationId, name, latitude, longitude))
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process : $e")
                    }

                    callback(stations)
                }
                catch(e: Exception) {
                    println("Error during decoding process : $e")
                }
            }
        }*/

        fun getStationsByPath(pathId: String, callback: (List<Station>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/process/saeiv_arrets_chemin?key=0234ABEFGH&datainputs={\"gid\":$pathId}") { responseBody ->
                try {
                    val stations: MutableList<Station> = mutableListOf()
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
                            val id = featuresJSONObject.getJSONObject("properties").getString("gid")
                            val name = featuresJSONObject.getJSONObject("properties").getString("libelle")
                            val stationId = featuresJSONObject.getJSONObject("properties").getString("ident")

                            stations.add(Station(id, stationId, name, latitude, longitude))
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process : $e")
                    }

                    callback(stations)
                }
                catch(e: Exception) {
                    println("Error during decoding process : $e")
                }
            }
        }

        fun getGTFSStationByPaths(pathIds: List<String>, network: String): List<Station> {
            val stationsToReturn = mutableListOf<Station>()
            try {
                runBlocking {
                    val rawResult = supabase.postgrest
                        .rpc("get_stops_by_shape", mapOf("shape_ids" to pathIds, "network" to network))
                    val jsonStations = JSONArray(rawResult.data)
                    for(i in 0 until jsonStations.length()) {
                        //val jsonStation = jsonStations.getJSONObject(i)
                        /*stationsToRetu/n.add(
                            Station(
                                jsonStation.getString("id"),
                                //jsonStation.
                            )*/
                        //)
                    }
                }
                return stationsToReturn
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
            return stationsToReturn
        }

        fun getGTFSStationById(id: String, network: String): Station {
            var station = Station("", "", "Arrêt inconnu",  0.0, 0.0)
            try {
                runBlocking {
                    val rawResult = supabase
                        .from("stops")
                        .select {
                            filter {
                                and {
                                    eq("network", network)
                                    eq("stop_id", id)
                                }
                            }
                        }
                    val jsonStation = JSONArray(rawResult.data).getJSONObject(0)
                    station = Station(
                        id = jsonStation.getString("stop_id") ?: "",
                        stationId = jsonStation.getString("stop_code") ?: "",
                        name = jsonStation.getString("stop_name") ?: "Arrêt inconnu",
                        latitude = jsonStation.getDouble("stop_lat"),
                        longitude = jsonStation.getDouble("stop_lon")
                    )
                }
                return station
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
            return station
        }
    }
}