package model.DAO

import com.google.transit.realtime.GtfsRealtime
import kotlinx.serialization.json.Json
import model.CallAPI
import model.DTO.Service
import model.helpers.toASCIIDecimal
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class ServiceDAO {
    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")

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
                        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
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
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu"),
                            path = propertiesJSONObject.getInt("rs_sv_chem_l"),
                            timestamp = dateFormat.parse(propertiesJSONObject.getString("mdate")) ?: Date()
                        ))
                    }
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }

                callback(services)
            }
        }

        fun getAmetisRawServices(callback: (ArrayList<Service>) -> Unit) {
            CallAPI.runGTFSRT("https://proxy.transport.data.gouv.fr/resource/ametis-amiens-gtfs-rt-vehicle-position") { response ->
                response.body?.byteStream().use { inputStream ->
                    inputStream?.let {
                        val feedMessage = GtfsRealtime.FeedMessage.parseFrom(inputStream)
                        feedMessage?.let { fm ->
                            val services: ArrayList<Service> = arrayListOf()

                            fm.entityList.forEach { feedEntity ->
                                val vehicle = feedEntity.vehicle
                                val trip = vehicle.trip
                                val position = vehicle.position

                                services.add(
                                    Service(
                                        id = feedEntity.id.toIntOrNull() ?: 0,
                                        vehicleId = feedEntity.id.toIntOrNull() ?: 0,
                                        lineId = trip.routeId.toASCIIDecimal(),
                                        currentSpeed = position.speed.toInt(),
                                        state = "",
                                        stateTime = 0,
                                        destination = trip.directionId.toString(),
                                        latitude = position.latitude.toDouble(),
                                        longitude = position.longitude.toDouble(),
                                        currentStop = 0,
                                        path = 0,
                                        timestamp = Date()
                                    )
                                )
                            }

                            callback(services)
                        } ?: run {
                            callback(arrayListOf())
                        }
                    } ?: run {
                        callback(arrayListOf())
                    }
                }
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
                        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
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
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu"),
                            path = propertiesJSONObject.getInt("rs_sv_chem_l"),
                            timestamp = dateFormat.parse(propertiesJSONObject.getString("mdate")) ?: Date()
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
                    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
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
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu"),
                            path = propertiesJSONObject.getInt("rs_sv_chem_l"),
                            timestamp = dateFormat.parse(propertiesJSONObject.getString("mdate")) ?: Date()
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