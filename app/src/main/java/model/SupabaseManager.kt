package model

import com.diplay.ouestmonbus.MainApplication
import io.github.jan.supabase.postgrest.from
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
    companion object {
        private val supabase = MainApplication.supabase

        suspend fun beginSyncDatabaseProcess(callback: (Boolean) -> Unit) {
            val lines = supabase
                .from("lines")
                .select()
                .decodeList<Line>()
            Lines.insertLines(lines)

            val destinations = supabase
                .from("destinations")
                .select()
                .decodeList<Destination>()
            Destinations.insertDestinations(destinations)

            val allerDestinations = supabase
                .from("aller_destinations")
                .select()
                .decodeList<AllerDestination>()
            AllerDestinations.insertAllerDestinations(allerDestinations)

            val retourDestinations = supabase
                .from("retour_destinations")
                .select()
                .decodeList<RetourDestination>()
            RetourDestinations.insertRetourDestinations(retourDestinations)

            val nextSchedulesDestinations = supabase
                .from("next_schedules_destinations")
                .select()
                .decodeList<NextSchedulesDestination>()
            NextSchedulesDestinations.insertNextSchedulesDestinations(nextSchedulesDestinations)

            val pathDestinations = supabase
                .from("path_destinations")
                .select()
                .decodeList<PathDestination>()
            PathDestinations.insertPathDestinations(pathDestinations)

            val vehicles = supabase
                .from("vehicles")
                .select()
                .decodeList<Vehicle>()
            Vehicles.insertVehicles(vehicles)
        }
    }
}