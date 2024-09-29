package model

import android.content.Context
import com.diplay.ouestmonbus.MainApplication
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.DTO.AllerDestination
import model.DTO.AllerDestinations
import model.DTO.Destination
import model.DTO.Destinations
import model.DTO.Line
import model.DTO.Lines
import model.DTO.NextSchedulesDestination
import model.DTO.NextSchedulesDestinations
import model.DTO.PathDestination
import model.DTO.PathDestinations
import model.DTO.RetourDestination
import model.DTO.RetourDestinations
import model.DTO.Vehicle
import model.DTO.Vehicles
import model.preferences_data_store.StoreGTFSURL
import model.preferences_data_store.store_tables_version.StoreAllerDestinationTableVersion
import model.preferences_data_store.store_tables_version.StoreDestinationTableVersion
import model.preferences_data_store.store_tables_version.StoreLineTableVersion
import model.preferences_data_store.store_tables_version.StoreNextSchedulesDestinationTableVersion
import model.preferences_data_store.store_tables_version.StorePathDestinationTableVersion
import model.preferences_data_store.store_tables_version.StoreRetourDestinationTableVersion
import model.preferences_data_store.store_tables_version.StoreVehicleTableVersion

class SupabaseManager {
    @Serializable
    class VersionDescriptor (
        val id: Int,
        @SerialName("table_name") val tableName: String,
        var version: String,
        var network: String,
        @SerialName("updated_at") val updatedAt: String
    )

    companion object {
        private val supabase = MainApplication.supabase

        suspend fun beginSyncDatabaseProcess(
            network: String,
            context: Context,
            callback: (String) -> Unit
        ) {
            try {
                checkTablesVersion(network, context) { callback(it) }
                val gtfsTripsURL = getGTFSURL("trips", network)
                StoreGTFSURL(context).setGTFSTripsURL(gtfsTripsURL)
            }
            catch (e: Exception) {
                print("Error : ${e.localizedMessage}")
            }
        }

        private suspend fun checkTablesVersion(
            network: String,
            context: Context,
            callback: (String) -> Unit
        ) {
            getVersions(network).forEach { descriptor ->
                when(descriptor.tableName) {
                    "lines" -> retrieveLines(network, descriptor.version, context) { callback(it) }
                    "destinations" -> retrieveDestinations(network, descriptor.version, context)
                    "aller_destinations" -> retrieveAllerDestinations(descriptor.version, context)
                    "retour_destinations" -> retrieveRetourDestinations(descriptor.version, context)
                    "next_schedules_destinations" ->
                        retrieveNextSchedulesDestinations(
                            onlineVersion = descriptor.version,
                            context = context
                        )
                    "path_destinations" -> retrievePathDestinations(descriptor.version, context)
                    "vehicles" -> retrieveVehicles(network, descriptor.version, context)
                }
            }
        }

        private suspend fun getVersions(network: String): List<VersionDescriptor> {
            try {
                return supabase
                    .from("table_version_descriptor")
                    .select { filter { eq("network", network) } }
                    .decodeList<VersionDescriptor>()
            }
            catch(e: Exception) {
                println(e.localizedMessage)
                return emptyList()
            }
        }

        private suspend fun retrieveLines(
            network: String,
            onlineVersion: String,
            context: Context,
            callback: (String) -> Unit
        ) {
            try {
                val storeLinePreferences = StoreLineTableVersion(context)
                storeLinePreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        Lines.deleteContent()
                        val lines = supabase
                            .from("lines")
                            .select { filter { eq("network", network) } }
                            .decodeList<Line>()
                        Lines.insertLines(lines)
                        callback("reload")
                        storeLinePreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        private suspend fun retrieveDestinations(
            network: String,
            onlineVersion: String,
            context: Context
        ) {
            try {
                val storeDestinationPreferences = StoreDestinationTableVersion(context)
                storeDestinationPreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        Destinations.deleteContent()
                        val destinations = supabase
                            .from("destinations")
                            .select { filter { eq("network", network) } }
                            .decodeList<Destination>()
                        Destinations.insertDestinations(destinations)
                        storeDestinationPreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        private suspend fun retrieveAllerDestinations(onlineVersion: String, context: Context) {
            try {
                val storeAllerDestinationPreferences = StoreAllerDestinationTableVersion(context)
                storeAllerDestinationPreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        AllerDestinations.deleteContent()
                        val allerDestinations = supabase
                            .from("aller_destinations")
                            .select { filter { eq("network", "tbm") } }
                            .decodeList<AllerDestination>()
                        AllerDestinations.insertAllerDestinations(allerDestinations)
                        storeAllerDestinationPreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        private suspend fun retrieveRetourDestinations(onlineVersion: String, context: Context) {
            try {
                val storeRetourDestinationPreferences = StoreRetourDestinationTableVersion(context)
                storeRetourDestinationPreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        RetourDestinations.deleteContent()
                        val retourDestinations = supabase
                            .from("retour_destinations")
                            .select { filter { eq("network", "tbm") } }
                            .decodeList<RetourDestination>()
                        RetourDestinations.insertRetourDestinations(retourDestinations)
                        storeRetourDestinationPreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        private suspend fun retrieveNextSchedulesDestinations(
            onlineVersion: String,
            context: Context
        ) {
            try {
                val storeNextSchedulesDestinationPreferences =
                    StoreNextSchedulesDestinationTableVersion(context)
                storeNextSchedulesDestinationPreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        NextSchedulesDestinations.deleteContent()
                        val nextSchedulesDestinations = supabase
                            .from("next_schedules_destinations")
                            .select { filter { eq("network", "tbm") } }
                            .decodeList<NextSchedulesDestination>()
                        NextSchedulesDestinations.insertNextSchedulesDestinations(
                            nsDestinations = nextSchedulesDestinations
                        )
                        storeNextSchedulesDestinationPreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        private suspend fun retrievePathDestinations(onlineVersion: String, context: Context) {
            try {
                val storePathDestinationPreferences = StorePathDestinationTableVersion(context)
                storePathDestinationPreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        PathDestinations.deleteContent()
                        val pathDestinations = supabase
                            .from("path_destinations")
                            .select { filter { eq("network", "tbm") } }
                            .decodeList<PathDestination>()
                        PathDestinations.insertPathDestinations(pathDestinations)
                        storePathDestinationPreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        private suspend fun retrieveVehicles(
            network: String,
            onlineVersion: String,
            context: Context
        ) {
            try {
                val storeVehiclePreferences = StoreVehicleTableVersion(context)
                storeVehiclePreferences.version.firstOrNull().let {
                    if(onlineVersion != it) {
                        Vehicles.deleteContent()
                        val vehicles = supabase
                            .from("vehicles")
                            .select { filter { eq("network", network) } }
                            .decodeList<Vehicle>()
                        Vehicles.insertVehicles(vehicles)
                        storeVehiclePreferences.setCurrentVersion(onlineVersion)
                    }
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
            }
        }

        suspend fun getGTFSURL(file: String, network: String): String {
            val rawUrl = supabase
                .from("gtfs_link_descriptor")
                .select(Columns.list("url")) {
                    filter {
                        and {
                            eq("network", network)
                            eq("file", file)
                        }
                    }
                }
                .decodeList<Map<String, String>>()
            return rawUrl.firstOrNull()?.values?.firstOrNull() ?: ""
        }
    }
}