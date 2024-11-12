package model.DAO

import android.content.Context
import com.google.transit.realtime.GtfsRealtime
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import model.CallAPI
import model.DTO.GTFSService
import model.DTO.Service
import model.helpers.toASCIIDecimal
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

const val AMETIS_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/ametis-amiens-gtfs-rt-vehicle-position"
const val STAR_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/star-rennes-integration-gtfs-rt-vehicle-position"
const val COROLIS_GTFS_RT_VEHICLE_POSITIONS_URL = "https://api.oisemob.cityway.fr/dataflow/vehicule-tc-tr/download?provider=COROLIS_URB&dataFormat=gtfs-rt"
const val TAM_GTFS_RT_VEHICLE_POSITIONS_URL = "https://data.montpellier3m.fr/TAM_MMM_GTFSRT/VehiclePosition.pb"

class ServiceDAO {
    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")

        fun getAllTBMServices(callback: (ArrayList<Service>) -> Unit) {
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

        fun getAllServicesFromGTFSRT(
            network: String,
            context: Context,
            withoutDestination: Boolean,
            callback: (ArrayList<Service>) -> Unit
        ) {
            val urlToCall = when(network) {
                "ametis" -> AMETIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "star" -> STAR_GTFS_RT_VEHICLE_POSITIONS_URL
                "corolis" -> COROLIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "tam" -> TAM_GTFS_RT_VEHICLE_POSITIONS_URL
                else -> ""
            }
            CallAPI.runGTFSRT(urlToCall) { response ->
                response.body?.byteStream().use { inputStream ->
                    inputStream?.let {
                        val feedMessage = FeedMessage.parseFrom(inputStream)
                        feedMessage?.let { fm ->
                            if(withoutDestination) {
                                rawGTFSToServices(
                                    feedMessage = fm,
                                    network = network,
                                    callback = callback
                                )
                            } else {
                                GTFSService.getTrips(network, context) { trips ->
                                    rawGTFSToServices(
                                        feedMessage = fm,
                                        network = network,
                                        trips = trips,
                                        callback = callback
                                    )
                                }
                            }
                        } ?: run {
                            callback(arrayListOf())
                        }
                    } ?: run {
                        callback(arrayListOf())
                    }
                }
            }
        }

        private fun rawGTFSToServices(
            feedMessage: FeedMessage,
            network: String,
            trips: List<Map<String, String>>? = null,
            callback: (ArrayList<Service>) -> Unit
        ) {
            val services: ArrayList<Service> = arrayListOf()

            feedMessage.entityList.forEach { feedEntity ->
                val id = when(network) {
                    "ametis", "star", "tam" -> feedEntity.id.toIntOrNull() ?: 0
                    "corolis" -> feedEntity.id.removeRange(0..2).toIntOrNull() ?: 0
                    else -> 0
                }
                val vehicle = feedEntity.vehicle
                val position = vehicle.position
                val trip = vehicle.trip

                val tripId = when(network) {
                    "tam" -> processTamTripId(trip.tripId)
                    else -> trip.tripId
                }
                val tripHeadsign = GTFSService.findTripHeadsign(
                    tripId = tripId,
                    trips = trips ?: listOf()
                ) ?: "Destination inconnue"
                val lineId = when(network) {
                    "ametis", "corolis" -> trip.routeId.toASCIIDecimal()
                    "star", "tam" -> (trip.routeId ?: "").drop(2).toIntOrNull() ?: 0
                    else -> 0
                }
                val vehicleId = when(network) {
                    "ametis", "star", "tam" -> feedEntity.id.toIntOrNull() ?: 0
                    "corolis" -> vehicle.vehicle.id.removeRange(0..2).toIntOrNull() ?: 0
                    else -> 0
                }

                services.add(
                    Service(
                        id = id,
                        vehicleId = vehicleId,
                        lineId = lineId,
                        currentSpeed = position.speed.toInt(),
                        state = "UNKNOWN",
                        stateTime = 0,
                        destination = if (trips == null)
                            vehicle.trip.directionId.toString()
                        else
                            tripHeadsign.replace(oldValue = "/", newValue = ""),
                        latitude = position.latitude.toDouble(),
                        longitude = position.longitude.toDouble(),
                        currentStop = 0,
                        path = 0,
                        timestamp = Date()
                    )
                )
            }

            callback(services)
        }

        private fun processTamTripId(tripId: String): String {
            var processedTripId = ""
            for(i in tripId.indices) {
                val char = tripId[i]
                if(char == '-') {
                    return processedTripId
                }
                processedTripId += tripId[i]
            }
            return processedTripId
        }

        fun getTBMServicesByLine(lineId: Int, callback: (ArrayList<Service>) -> Unit) {
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

        fun getTBMServiceByVehicleId(vehicleId: Int, callback: (Service?) -> Unit) {
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