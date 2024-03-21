package model.DAO

import model.DAO.AccessData.CallAPI
import model.DTO.Service
import org.json.JSONObject

class ServiceDAO {
    companion object {
        fun getAllServices(callback: (ArrayList<Service>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson?key=0234ABEFGH&typename=sv_vehic_p") { responseBody ->
                val services: ArrayList<Service> = arrayListOf()
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    for(i in 0 until featuresJSONArray.length()) {
                        val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                        val latitude = if (featuresJSONObject.isNull("geometry"))
                            0.0
                        else
                            featuresJSONObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1)
                        val longitude = if (featuresJSONObject.isNull("geometry"))
                            0.0
                        else
                            featuresJSONObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0)
                        val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")
                        services.add(Service(
                            id = propertiesJSONObject.getInt("gid"),
                            vehicleId = propertiesJSONObject.getInt("gid"),
                            lineId = propertiesJSONObject.getInt("rs_sv_ligne_a"),
                            currentSpeed = propertiesJSONObject.getInt("vitesse"),
                            state = propertiesJSONObject.getString("etat"),
                            stateTime = propertiesJSONObject.getInt("retard"),
                            destination = propertiesJSONObject.getString("terminus"),
                            latitude = latitude,
                            longitude = longitude,
                            currentStop = if (propertiesJSONObject.isNull("rs_sv_arret_p_actu"))
                                0
                            else
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu")
                        ))
                    }
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }

                callback(services)
            }
        }

        fun getServicesByLine(lineId: Int, callback: (ArrayList<Service>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_vehic_p?key=0234ABEFGH&filter={\"rs_sv_ligne_a\":$lineId}") { responseBody ->
                val services: ArrayList<Service> = arrayListOf()
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    for(i in 0 until featuresJSONArray.length()) {
                        val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                        val latitude = if (featuresJSONObject.isNull("geometry"))
                            0.0
                        else
                            featuresJSONObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1)
                        val longitude = if (featuresJSONObject.isNull("geometry"))
                            0.0
                        else
                            featuresJSONObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0)
                        val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")
                        services.add(Service(
                            id = propertiesJSONObject.getInt("gid"),
                            vehicleId = propertiesJSONObject.getInt("gid"),
                            lineId = propertiesJSONObject.getInt("rs_sv_ligne_a"),
                            currentSpeed = propertiesJSONObject.getInt("vitesse"),
                            state = propertiesJSONObject.getString("etat"),
                            stateTime = propertiesJSONObject.getInt("retard"),
                            destination = propertiesJSONObject.getString("terminus"),
                            latitude = latitude,
                            longitude = longitude,
                            currentStop = if (propertiesJSONObject.isNull("rs_sv_arret_p_actu"))
                                0
                            else
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu")
                        ))
                    }
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }

                callback(services)
            }
        }

        fun getServiceByVehicleId(vehicleId: Int, callback: (Service?) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson?key=0234ABEFGH&typename=sv_vehic_p&filter={\"gid\":$vehicleId}") { responseBody ->
                val welcomeJSONObject = JSONObject(responseBody)
                val featuresJSONArray = welcomeJSONObject.getJSONArray("features")

                try {
                    val featuresJSONObject = featuresJSONArray.getJSONObject(0)
                    val propertiesJSONObject = featuresJSONObject.getJSONObject("properties")
                    val latitude = if (featuresJSONObject.isNull("geometry"))
                        0.0
                    else
                        featuresJSONObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1)
                    val longitude = if (featuresJSONObject.isNull("geometry"))
                        0.0
                    else
                        featuresJSONObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0)
                    callback(
                        Service(
                            id = propertiesJSONObject.getInt("gid"),
                            vehicleId = propertiesJSONObject.getInt("gid"),
                            lineId = propertiesJSONObject.getInt("rs_sv_ligne_a"),
                            currentSpeed = propertiesJSONObject.getInt("vitesse"),
                            state = propertiesJSONObject.getString("etat"),
                            stateTime = propertiesJSONObject.getInt("retard"),
                            destination = propertiesJSONObject.getString("terminus"),
                            latitude = latitude,
                            longitude = longitude,
                            currentStop = if (propertiesJSONObject.isNull("rs_sv_arret_p_actu"))
                                0
                            else
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu")
                        )
                    )
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                    callback(null)
                }
            }
        }
    }
}