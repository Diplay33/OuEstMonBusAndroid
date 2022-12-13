package model.DAO

import model.DAO.AccessData.CallAPI
import model.DTO.Path
import org.json.JSONObject

class PathDAO {
    companion object {
        fun getPathsByLine(lineId: Int, callback: (List<Path>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_chem_l?key=0234ABEFGH&filter={\"rs_sv_ligne_a\":$lineId,\"principal\":true}&attributes=[\"gid\",\"libelle\",\"sens\"]") { responseBody ->
                val paths: MutableList<Path> = mutableListOf()
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    for(i in 0 until featuresJSONArray.length()) {
                        val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                        val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")

                        paths.add(
                            Path(
                                id = propertiesJSONObject.getInt("gid"),
                                name = propertiesJSONObject.getString("libelle"),
                                direction = propertiesJSONObject.getString("sens")
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
    }
}