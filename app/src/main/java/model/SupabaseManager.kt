package model

import com.diplay.ouestmonbus.MainApplication
import io.github.jan.supabase.postgrest.from
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

        suspend fun beginSyncDatabaseProcess(callback: (String) -> Unit) {
            checkTablesVersion()
            callback("reload")
        }

        private suspend fun checkTablesVersion() {
            getVersions().forEach {
                when(it.tableName) {
                    "lines" -> retrieveLines()
                    "destinations" -> retrieveDestinations()
                    "aller_destinations" -> retrieveAllerDestinations()
                    "retour_destinations" -> retrieveRetourDestinations()
                    "next_schedules_destinations" -> retrieveNextSchedulesDestinations()
                    "path_destinations" -> retrievePathDestinations()
                    "vehicles" -> retrieveVehicles()
                }
            }
        }

        private suspend fun getVersions(): List<VersionDescriptor> {
            return supabase
                .from("table_version_descriptor")
                .select()
                .decodeList<VersionDescriptor>()
        }

        private suspend fun retrieveLines() {
            Lines.deleteContent()
            val lines = supabase
                .from("lines")
                .select()
                .decodeList<Line>()
            Lines.insertLines(lines)
        }

        private suspend fun retrieveDestinations() {
            Destinations.deleteContent()
            val destinations = supabase
                .from("destinations")
                .select()
                .decodeList<Destination>()
            Destinations.insertDestinations(destinations)
        }

        private suspend fun retrieveAllerDestinations() {
            AllerDestinations.deleteContent()
            val allerDestinations = supabase
                .from("aller_destinations")
                .select()
                .decodeList<AllerDestination>()
            AllerDestinations.insertAllerDestinations(allerDestinations)
        }

        private suspend fun retrieveRetourDestinations() {
            RetourDestinations.deleteContent()
            val retourDestinations = supabase
                .from("retour_destinations")
                .select()
                .decodeList<RetourDestination>()
            RetourDestinations.insertRetourDestinations(retourDestinations)
        }

        private suspend fun retrieveNextSchedulesDestinations() {
            NextSchedulesDestinations.deleteContent()
            val nextSchedulesDestinations = supabase
                .from("next_schedules_destinations")
                .select()
                .decodeList<NextSchedulesDestination>()
            NextSchedulesDestinations.insertNextSchedulesDestinations(nextSchedulesDestinations)
        }

        private suspend fun retrievePathDestinations() {
            PathDestinations.deleteContent()
            val pathDestinations = supabase
                .from("path_destinations")
                .select()
                .decodeList<PathDestination>()
            PathDestinations.insertPathDestinations(pathDestinations)
        }

        private suspend fun retrieveVehicles() {
            Vehicles.deleteContent()
            val vehicles = supabase
                .from("vehicles")
                .select()
                .decodeList<Vehicle>()
            Vehicles.insertVehicles(vehicles)
        }
    }
}