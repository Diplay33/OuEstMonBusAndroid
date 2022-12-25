package model.DAO

import model.DAO.AccessData.CallAPI
import model.DTO.NextSchedule
import org.json.JSONObject

class NextScheduleDAO {
    companion object {
        fun getNextSchedulesByStationId(stationId: String, callback: (List<NextSchedule>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/process/saeiv_arret_passages?key=0234ABEFGH&datainputs={\"arret_id\":\"$stationId\",\"duration\":59}") { responseBody ->
                try {
                    val nextSchedules: MutableList<NextSchedule> = mutableListOf()
                    val welcomeJSONObject = JSONObject(responseBody)
                    val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                    try {
                        for(i in 0 until featuresJSONArray.length()) {
                            val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                            val propertiesJSONObject =
                                featuresJSONObject.getJSONObject("properties")

                            nextSchedules.add(NextSchedule(
                                lineId = propertiesJSONObject.getInt("rs_sv_ligne_a"),
                                destination = propertiesJSONObject.getString("terminus"),
                                vehicleId = if (propertiesJSONObject.isNull("rs_sv_vehic_p"))
                                    null
                                else
                                    propertiesJSONObject.getInt("rs_sv_vehic_p"),
                                rawTheoricTime = propertiesJSONObject.getString("hor_app"),
                                rawEstimatedTime = propertiesJSONObject.getString("hor_estime")
                            ))
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process: $e")
                    }

                    callback(nextSchedules)
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }
            }
        }
    }
}