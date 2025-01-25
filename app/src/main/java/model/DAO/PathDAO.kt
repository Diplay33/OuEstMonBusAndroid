package model.DAO

import com.diplay.ouestmonbus.MainApplication
import model.CallAPI
import model.DTO.Path
import org.json.JSONObject

class PathDAO {
    companion object {
        private val supabase = MainApplication.gtfsSupabase

        fun getTBMPathsByLine(lineId: Int, withCoordinates: Boolean, callback: (List<Path>) -> Unit) {
            var url = "https://data.bordeaux-metropole.fr/geojson/features/sv_chem_l?key=0234ABEFGH&filter={\"rs_sv_ligne_a\":$lineId,\"principal\":true}"
            if(!withCoordinates) {
                url += "&attributes=[\"gid\",\"libelle\",\"sens\"]"
            }
            CallAPI.run(url) { responseBody ->
                val paths: MutableList<Path> = mutableListOf()
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    for(i in 0 until featuresJSONArray.length()) {
                        val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                        val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")
                        val coordinates = mutableListOf<List<List<Double>>>()

                        if(withCoordinates) {
                            val geometryJSONObject = featuresJSONObject.getJSONObject("geometry")
                            val coordinatesJSONArray = geometryJSONObject.getJSONArray("coordinates")

                            try {
                                val pathArray = mutableListOf<List<Double>>()
                                for(j in 0 until coordinatesJSONArray.length()) {
                                    pathArray.add(
                                        listOf(
                                            coordinatesJSONArray.getJSONArray(j).getDouble(0),
                                            coordinatesJSONArray.getJSONArray(j).getDouble(1)
                                        )
                                    )
                                }
                                coordinates.add(pathArray)
                            }
                            catch(_: Exception) {
                                for(j in 0 until coordinatesJSONArray.length()) {
                                    val pathArray = mutableListOf<List<Double>>()
                                    val tempArray = coordinatesJSONArray.getJSONArray(j)
                                    for(k in 0 until tempArray.length()) {
                                        pathArray.add(
                                            listOf(
                                                tempArray.getJSONArray(k).getDouble(0),
                                                tempArray.getJSONArray(k).getDouble(1)
                                            )
                                        )
                                    }
                                    coordinates.add(pathArray)
                                }
                            }
                        }

                        paths.add(
                            Path(
                                id = propertiesJSONObject.getInt("gid").toString(),
                                name = propertiesJSONObject.getString("libelle"),
                                direction = propertiesJSONObject.getString("sens"),
                                coordinates = coordinates
                            )
                        )
                    }
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }

                callback(paths)
            }
        }

        fun getPathsByLineFromGTFS(network: String, lineId: Int): List<Path> {
            return listOf()
        }

        fun getAllPathsByLine(lineId: Int, callback: (List<Path>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_chem_l?key=0234ABEFGH&filter={\"rs_sv_ligne_a\":$lineId}&attributes=[\"gid\",\"libelle\",\"sens\"]") { responseBody ->
                val paths: MutableList<Path> = mutableListOf()
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    for(i in 0 until featuresJSONArray.length()) {
                        val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                        val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")

                        paths.add(
                            Path(
                                id = propertiesJSONObject.getInt("gid").toString(),
                                name = propertiesJSONObject.getString("libelle"),
                                direction = propertiesJSONObject.getString("sens"),
                                coordinates = listOf()
                            )
                        )
                    }
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }

                callback(paths)
            }
        }

        fun getPath(id: Int, withCoordinates: Boolean, callback: (Path?) -> Unit) {
            var url = "https://data.bordeaux-metropole.fr/geojson/features/SV_CHEM_L?key=0234ABEFGH&filter={\"gid\":$id}"
            if(!withCoordinates) {
                url += "&attributes=[\"gid\",\"libelle\",\"sens\",\"principal\"]"
            }
            CallAPI.run(url) { responseBody ->
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    for(i in 0 until featuresJSONArray.length()) {
                        val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                        val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")
                        val coordinates = mutableListOf<List<List<Double>>>()

                        if(withCoordinates) {
                            val geometryJSONObject = featuresJSONObject.getJSONObject("geometry")
                            val coordinatesJSONArray = geometryJSONObject.getJSONArray("coordinates")

                            try {
                                val pathArray = mutableListOf<List<Double>>()
                                for(j in 0 until coordinatesJSONArray.length()) {
                                    pathArray.add(
                                        listOf(
                                            coordinatesJSONArray.getJSONArray(j).getDouble(0),
                                            coordinatesJSONArray.getJSONArray(j).getDouble(1)
                                        )
                                    )
                                }
                                coordinates.add(pathArray)
                            }
                            catch(_: Exception) {
                                for(j in 0 until coordinatesJSONArray.length()) {
                                    val pathArray = mutableListOf<List<Double>>()
                                    val tempArray = coordinatesJSONArray.getJSONArray(j)
                                    for(k in 0 until tempArray.length()) {
                                        pathArray.add(
                                            listOf(
                                                tempArray.getJSONArray(k).getDouble(0),
                                                tempArray.getJSONArray(k).getDouble(1)
                                            )
                                        )
                                    }
                                    coordinates.add(pathArray)
                                }
                            }
                        }

                        callback(
                            Path(
                                id = propertiesJSONObject.getInt("gid").toString(),
                                name = propertiesJSONObject.getString("libelle"),
                                direction = propertiesJSONObject.getString("sens"),
                                coordinates = coordinates
                            )
                        )
                    }
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                    callback(null)
                }
            }
        }
    }
}