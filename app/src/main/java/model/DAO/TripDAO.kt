package model.DAO

import com.diplay.ouestmonbus.MainApplication
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.runBlocking

class TripDAO {
    companion object {
        private val supabase = MainApplication.gtfsSupabase

        fun getTripHeadsigns(ids: List<String>, network: String, callback: (List<Map<String, String?>>) -> Unit) {
            try {
                runBlocking {
                    val results = supabase
                        .from("trips")
                        .select(Columns.list("trip_id", "trip_headsign")) {
                            filter {
                                and {
                                    isIn("trip_id", ids)
                                    eq("network", network)
                                }
                            }
                        }
                        .decodeList<Map<String, String?>>()
                    callback(results)
                }
            }
            catch(e: Exception) {
                println(e.localizedMessage)
                callback(listOf())
            }
        }
    }
}