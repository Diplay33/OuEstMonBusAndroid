package model.DAO

import com.google.transit.realtime.GtfsRealtime
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import model.CallAPI
import model.DTO.Destinations
import model.DTO.Lines
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
const val FILBLEU_GTFS_RT_VEHICLE_POSITIONS_URL = "https://data.filbleu.fr/ws-tr/gtfs-rt/opendata/vehicle-positions"
const val ASTUCE_GTFS_RT_VEHICLE_POSITIONS_URL = "https://www.reseau-astuce.fr/ftp/gtfsrt/Astuce.VehiclePosition.pb"
const val PALMBUS_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/palmbus-cannes-gtfs-rt-vehicle-position"
const val TANGO_GTFS_RT_VEHICLE_POSITIONS_URL = "https://transport.data.gouv.fr/resources/80732/download"
const val VIB_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/le-vib-vierzon-gtfs-rt-vehicle-position"
const val SURF_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/surf-fougeres-gtfs-rt-vehicle-position"
const val CAPCOTENTIN_GTFS_RT_VEHICLE_POSITIONS_URL = "https://app.pysae.com/api/v2/groups/transdev-cotentin/gtfs-rt"
const val BIBUS_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/bibus-brest-gtfs-rt-vehicle-position"
const val AXO_GTFS_RT_VEHICLE_POSITIONS_URL = "https://api.oisemob.cityway.fr/dataflow/vehicule-tc-tr/download?provider=AXO&dataFormat=gtfs-rt"
const val ZEST_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/zest-menton-riviera-gtfs-rt-vehicle-position"
const val TCAT_GTFS_RT_VEHICLE_POSITIONS_URL = "https://transport.data.gouv.fr/resources/81543/download"
const val DIVIA_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/divia-dijon-gtfs-rt-vehicle-position"
const val CITEA_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/citea-valence-gtfs-rt-vehicle-position"
const val TBK_GTFS_RT_VEHICLE_POSITIONS_URL = "https://pysae.com/api/v2/groups/quimperle/gtfs-rt"
const val SAM_GTFS_RT_VEHICLE_POSITIONS_URL = "https://sete.ceccli.com/gtfs/VehiclePositions.pb"
const val TIC_GTFS_RT_VEHICLE_POSITIONS_URL = "https://api.oisemob.cityway.fr/dataflow/vehicule-tc-tr/download?provider=TIC_URB&dataFormat=gtfs-rt"
const val MOOVA_GTFS_RT_VEHICLE_POSITIONS_URL = "https://proxy.transport.data.gouv.fr/resource/moova-vesoul-gtfs-rt-vehicle-position"
const val MISTRAL_GTFS_RT_VEHICLE_POSITIONS_URL = "https://feed-rdtpm-toulon.ratpdev.com/VehiclePosition/GTFS-RT"

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
            CallAPI.runGTFSRT(gtfsUrlToCall(network)) { response ->
                response.body.byteStream().use { inputStream ->
                    val feedMessage = FeedMessage.parseFrom(inputStream)
                    feedMessage?.let { fm ->
                        when(network) {
                            "filbleu" -> rawGTFSToServicesWithTrips(fm, network = network, callback = callback)
                            else -> rawGTFSToServices(fm, network, callback = callback)
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
            CallAPI.runGTFSRT(gtfsUrlToCall(network)) { response ->
                response.body.byteStream().use { inputStream ->
                    val feedMessage = FeedMessage.parseFrom(inputStream)
                    feedMessage?.let { fm ->
                        when(network) {
                            "filbleu" -> rawGTFSToServicesWithTrips(fm, lineId, network, callback)
                            else -> rawGTFSToServices(fm, network, lineId, callback)
                        }
                    } ?: run {
                        callback(arrayListOf())
                    }
                }
            }
        }

        private fun gtfsUrlToCall(network: String): String {
            return when(network) {
                "ametis" -> AMETIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "star" -> STAR_GTFS_RT_VEHICLE_POSITIONS_URL
                "corolis" -> COROLIS_GTFS_RT_VEHICLE_POSITIONS_URL
                "tam" -> TAM_GTFS_RT_VEHICLE_POSITIONS_URL
                "met" -> MET_GTFS_RT_VEHICLE_POSITIONS_URL
                "kiceo" -> KICEO_GTFS_RT_VEHICLE_POSITIONS_URL
                "irigo" -> IRIGO_GTFS_RT_VEHICLE_POSITIONS_URL
                "filbleu" -> FILBLEU_GTFS_RT_VEHICLE_POSITIONS_URL
                "astuce" -> ASTUCE_GTFS_RT_VEHICLE_POSITIONS_URL
                "palmbus" -> PALMBUS_GTFS_RT_VEHICLE_POSITIONS_URL
                "tango" -> TANGO_GTFS_RT_VEHICLE_POSITIONS_URL
                "vib" -> VIB_GTFS_RT_VEHICLE_POSITIONS_URL
                "surf" -> SURF_GTFS_RT_VEHICLE_POSITIONS_URL
                "capcotentin" -> CAPCOTENTIN_GTFS_RT_VEHICLE_POSITIONS_URL
                "bibus" -> BIBUS_GTFS_RT_VEHICLE_POSITIONS_URL
                "axo" -> AXO_GTFS_RT_VEHICLE_POSITIONS_URL
                "zest" -> ZEST_GTFS_RT_VEHICLE_POSITIONS_URL
                "tcat" -> TCAT_GTFS_RT_VEHICLE_POSITIONS_URL
                "divia" -> DIVIA_GTFS_RT_VEHICLE_POSITIONS_URL
                "citea" -> CITEA_GTFS_RT_VEHICLE_POSITIONS_URL
                "tbk" -> TBK_GTFS_RT_VEHICLE_POSITIONS_URL
                "sam" -> SAM_GTFS_RT_VEHICLE_POSITIONS_URL
                "tic" -> TIC_GTFS_RT_VEHICLE_POSITIONS_URL
                "moova" -> MOOVA_GTFS_RT_VEHICLE_POSITIONS_URL
                "mistral" -> MISTRAL_GTFS_RT_VEHICLE_POSITIONS_URL
                else -> ""
            }
        }

        private fun rawGTFSToServicesWithTrips(
            feedMessage: FeedMessage,
            lineId: Int? = null,
            network: String,
            callback: (ArrayList<Service>) -> Unit
        ) {
            feedMessage.entityList?.let { entity ->
                Lines.getLineIds(entity.map { it.vehicle.trip.tripId ?: "" }, network) { tripIds ->
                    val compactedTrips = tripIds
                        .mapNotNull { it["trip_id"]?.let { tripId -> tripId to it["route_id"] } }
                        .filter { it.second != null }
                        .associate { it.first to it.second!! }
                    rawGTFSToServices(
                        feedMessage = feedMessage,
                        lineId = lineId,
                        network = network,
                        callback = callback,
                        trips = compactedTrips
                    )
                }
            }
        }

        private fun rawGTFSToServices(
            feedMessage: FeedMessage,
            network: String,
            lineId: Int? = null,
            callback: (ArrayList<Service>) -> Unit,
            trips: Map<String, String> = mapOf()
        ) {
            val services: ArrayList<Service> = arrayListOf()
            val gtfsEntities = feedMessage.entityList
                .filter { it.vehicle.toString() != "" }
            val filteredEntities = lineId?.let {
                gtfsEntities.filter {
                    val processedLineId = when(network) {
                        "star", "tam", "divia" -> (it.vehicle.trip.routeId ?: "").drop(2).toASCIIDecimal()
                        "kiceo" -> {
                            val routeId = it.vehicle.trip.routeId
                            if (routeId == "MOBICEO") 999 else routeId.filter { !it.isWhitespace() && it.isLetterOrDigit() }.replace("SAEIV", "").toASCIIDecimal()
                        }
                        "filbleu" -> trips[it.vehicle.trip.tripId ?: ""]?.replace("TTR:LINE:", "")?.toASCIIDecimal() ?: 0
                        "tbk" -> (it.vehicle.trip.routeId ?: "").dropLast(4).toASCIIDecimal()
                        else -> it.vehicle.trip.routeId.toASCIIDecimal() // default
                    }
                    lineId == processedLineId
                }
            } ?: run {
                gtfsEntities
            }
            val tripIds = filteredEntities.map {
                val tripId = it.vehicle.trip.tripId
                when (network) {
                    "tam" -> processTamTripId(tripId)
                    "tango" -> tripId.drop(3)
                    "surf" -> processSurfTripId(tripId)
                    else -> tripId
                }
            }
            Destinations.getTripHeadsigns(tripIds, network) { headsigns ->
                filteredEntities.forEach { feedEntity ->
                    val id = when(network) {
                        "corolis", "astuce", "axo", "tic" -> feedEntity.id.removeRange(0..2).toIntOrNull() ?: 0
                        else -> feedEntity.id.toIntOrNull() ?: 0 //default
                    }
                    val vehicle = feedEntity.vehicle
                    val position = vehicle.position
                    val trip = vehicle.trip

                    val tripId = when(network) {
                        "tam" -> processTamTripId(trip.tripId)
                        "tango" -> trip.tripId.drop(3)
                        "surf" -> processSurfTripId(trip.tripId)
                        else -> trip.tripId
                    }
                    val serviceLineId = when(network) {
                        "star", "tam", "divia" -> (trip.routeId ?: "").drop(2).toASCIIDecimal()
                        "kiceo" -> {
                            val routeId = trip.routeId
                            if (routeId == "MOBICEO") 999 else routeId.filter { !it.isWhitespace() && it.isLetterOrDigit() }.replace("SAEIV", "").toASCIIDecimal()
                        }
                        "filbleu" -> trips[trip.tripId ?: ""]?.replace("TTR:LINE:", "")?.toASCIIDecimal() ?: 0
                        "tbk" -> (trip.routeId ?: "").dropLast(4).toASCIIDecimal()
                        else -> trip.routeId.toASCIIDecimal() //default
                    }
                    val vehicleId = when(network) {
                        "ametis", "star", "tam", "met", "palmbus", "tango" -> feedEntity.id.toIntOrNull() ?: 0
                        "corolis", "axo", "tic" -> vehicle.vehicle.id.removeRange(0..2).toIntOrNull() ?: 0
                        "irigo", "astuce", "bibus" -> vehicle.vehicle.id.toIntOrNull() ?: 0
                        "tbk" -> vehicle.vehicle.label.replace(" ", "").toASCIIDecimal()
                        else -> vehicle.vehicle.label.toASCIIDecimal() //default
                    }

                    services.add(
                        Service(
                            id = id,
                            vehicleId = vehicleId,
                            lineId = serviceLineId,
                            currentSpeed = if (position.hasSpeed()) position.speed.toInt() else null,
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

        private fun processSurfTripId(tripId: String): String {
            var processedTripId = ""
            var dashCount = 0
            for(i in tripId.indices) {
                val char = tripId[i]
                if(char == '-') {
                    if(dashCount == 0) {
                        dashCount += 1
                    }
                    else {
                        return processedTripId
                    }
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