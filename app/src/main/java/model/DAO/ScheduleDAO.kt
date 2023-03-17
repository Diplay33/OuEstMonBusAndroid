package model.DAO

import model.DAO.AccessData.CallAPI
import model.DTO.Schedule
import org.json.JSONObject

class ScheduleDAO {
    companion object {
        fun getSchedulesByStationAndPathAndDate(
            stationId: String,
            date: String,
            callback: (List<Schedule>) -> Unit
        ) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/process/saeiv_arret_horaires?key=0234ABEFGH&datainputs={\"arret_id\":\"$stationId\",\"date\":\"$date\"}", retry = true) { responseBody ->
                try {
                    val schedules: MutableList<Schedule> = mutableListOf()
                    val welcomeJSONObject = JSONObject(responseBody)
                    val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                    try {
                        for(i in 0 until featuresJSONArray.length()) {
                            val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                            val propertiesJSONObject =
                                featuresJSONObject.getJSONObject("properties")

                            try {
                                schedules.add(
                                    Schedule(
                                        pathId = propertiesJSONObject.getInt("rs_sv_chem_l"),
                                        rawAppTime = propertiesJSONObject.getString("hor_app"),
                                        rawRealTime = propertiesJSONObject.getString("hor_real"),
                                        state = propertiesJSONObject.getString("etat")
                                    )
                                )
                            }
                            catch(e: Exception) {
                                println("Error during decoding process: $e")
                            }
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process: $e")
                    }

                    callback(schedules)
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }
            }
        }
    }
}