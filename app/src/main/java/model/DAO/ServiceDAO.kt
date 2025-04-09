package model.DAO

import com.google.transit.realtime.GtfsRealtime.FeedMessage
import model.CallAPI
import model.DTO.Destinations
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
const val MET_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/lemet-metz-gtfs-rt-vehicle-position"
const val KICEO_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/kiceo-vannes-gtfs-rt-vehicle-position"
const val IRIGO_GTFS_RT_VEHICLE_POSITIONS_URL = "https://ara-api.enroute.mobi/irigo/gtfs/vehicle-positions"

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
                                ""
                            else
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu").toString(),
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

        fun getAllServicesFromGTFSRT(network: String, callback: (ArrayList<Service>) -> Unit) {
            val urlToCall = when(network) {
                "ametis" -> AMETIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "star" -> STAR_GTFS_RT_VEHICLE_POSITIONS_URL
                "corolis" -> COROLIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "tam" -> TAM_GTFS_RT_VEHICLE_POSITIONS_URL
                "met" -> MET_GTFS_RT_VEHICLE_POSITIONS_URL
                "kiceo" -> KICEO_GTFS_RT_VEHICLE_POSITIONS_URL
                "irigo" -> IRIGO_GTFS_RT_VEHICLE_POSITIONS_URL
                else -> ""
            }
            CallAPI.runGTFSRT(urlToCall) { response ->
                response.body?.byteStream().use { inputStream ->
                    inputStream?.let {
                        val feedMessage = FeedMessage.parseFrom(inputStream)
                        feedMessage?.let { fm ->
                            rawGTFSToServices(
                                feedMessage = fm,
                                network = network,
                                callback = callback)
                        } ?: run {
                            callback(arrayListOf())
                        }
                    } ?: run {
                        callback(arrayListOf())
                    }
                }
            }
        }

        fun getServicesFromGTFSRT(
            lineId: Int,
            network: String,
            callback: (ArrayList<Service>) -> Unit
        ) {
            val urlToCall = when(network) {
                "ametis" -> AMETIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "star" -> STAR_GTFS_RT_VEHICLE_POSITIONS_URL
                "corolis" -> COROLIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "tam" -> TAM_GTFS_RT_VEHICLE_POSITIONS_URL
                "met" -> MET_GTFS_RT_VEHICLE_POSITIONS_URL
                "kiceo" -> KICEO_GTFS_RT_VEHICLE_POSITIONS_URL
                "irigo" -> IRIGO_GTFS_RT_VEHICLE_POSITIONS_URL
                else -> ""
            }
            CallAPI.runGTFSRT(urlToCall) { response ->
                response.body?.byteStream().use { inputStream ->
                    inputStream?.let {
                        val feedMessage = FeedMessage.parseFrom(inputStream)
                        feedMessage?.let { fm ->
                            rawGTFSToServices(fm, network, lineId, callback)
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
            lineId: Int? = null,
            callback: (ArrayList<Service>) -> Unit
        ) {
            val services: ArrayList<Service> = arrayListOf()
            val filteredEntities = lineId?.let {
                feedMessage.entityList.filter {
                    val processedLineId = when(network) {
                        "ametis", "corolis", "met", "irigo" -> it.vehicle.trip.routeId.toASCIIDecimal()
                        "star", "tam" -> (it.vehicle.trip.routeId ?: "").drop(2).toIntOrNull() ?: 0
                        "kiceo" -> {
                            val routeId = it.vehicle.trip.routeId
                            if (routeId == "MOBICEO") 999 else routeId.filter { !it.isWhitespace() && it.isLetterOrDigit() }.replace("SAEIV", "").toASCIIDecimal()
                        }
                        else -> 0
                    }
                    lineId == processedLineId
                }
            } ?: run {
                feedMessage.entityList
            }
            val tripIds = filteredEntities.map {
                val tripId = it.vehicle.trip.tripId
                when (network) {
                    "tam" -> processTamTripId(tripId)
                    else -> tripId
                }
            }
            Destinations.getTripHeadsigns(tripIds, network) { headsigns ->
                filteredEntities.forEach { feedEntity ->
                    val id = when(network) {
                        "ametis", "star", "tam", "met", "kiceo", "irigo" -> feedEntity.id.toIntOrNull() ?: 0
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
                    val serviceLineId = when(network) {
                        "ametis", "corolis", "met", "irigo" -> trip.routeId.toASCIIDecimal()
                        "star", "tam" -> (trip.routeId ?: "").drop(2).toIntOrNull() ?: 0
                        "kiceo" -> {
                            val routeId = trip.routeId
                            if (routeId == "MOBICEO") 999 else routeId.filter { !it.isWhitespace() && it.isLetterOrDigit() }.replace("SAEIV", "").toASCIIDecimal()
                        }
                        else -> 0
                    }
                    val vehicleId = when(network) {
                        "ametis", "star", "tam", "met" -> feedEntity.id.toIntOrNull() ?: 0
                        "corolis" -> vehicle.vehicle.id.removeRange(0..2).toIntOrNull() ?: 0
                        "kiceo" -> vehicle.vehicle.label.toIntOrNull() ?: 0
                        "irigo" -> vehicle.vehicle.id.toIntOrNull() ?: 0
                        else -> 0
                    }

                    services.add(
                        Service(
                            id = id,
                            vehicleId = vehicleId,
                            lineId = serviceLineId,
                            currentSpeed = position.speed.toInt(),
                            state = "UNKNOWN",
                            stateTime = 0,
                            destination = headsigns.firstOrNull { it["trip_id"] == tripId }?.get("trip_headsign") ?: "Destination inconnue",
                            latitude = position.latitude.toDouble(),
                            longitude = position.longitude.toDouble(),
                            //needs to be set at a default value, in order for Compose Navigation to work properly
                            currentStop = vehicle.stopId.ifEmpty { "0" },
                            path = 0,
                            timestamp = Date()
                        )
                    )
                }
                callback(services)
            }
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
                                ""
                            else
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu").toString(),
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
                                ""
                            else
                                propertiesJSONObject.getInt("rs_sv_arret_p_actu").toString(),
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