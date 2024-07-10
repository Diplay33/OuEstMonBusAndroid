package model.DTO

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.SupabaseManager
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

class GTFSService {
    companion object {
        fun getTrips(context: Context, callback: (List<Map<String, String>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val url = SupabaseManager.getGTFSURL("trips", "ametis")
                val destinationPath = "ametis_gtfs_trips.txt"

                if(url.contains("http")) {
                    downloadFile(context, url, destinationPath) { error ->
                        if (error != null) {
                            println("Erreur lors du téléchargement: ${error.message}")
                            callback(listOf())
                            return@downloadFile
                        }

                        val content = readFile(context, destinationPath)
                        callback(transformToDictionaryArray(content ?: ""))
                    }
                }
            }
        }

        private fun downloadFile(context: Context, url: String, fileName: String, callback: (Exception?) -> Unit) {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    callback(e)
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (!response.isSuccessful) {
                        callback(IOException("Erreur lors du téléchargement: ${response.code}"))
                        return
                    }
                    val file = File(context.filesDir, fileName)
                    file.writeBytes(response.body!!.bytes())
                    callback(null)
                }
            })
        }

        private fun readFile(context: Context, fileName: String): String? {
            return try {
                File(context.filesDir, fileName).readText(Charsets.UTF_8)
            } catch (e: IOException) {
                println("Erreur lors de la lecture du fichier: ${e.message}")
                null
            }
        }

        private fun transformToDictionaryArray(content: String): List<Map<String, String>> {
            val lines = content.split("\n")
            if (lines.isEmpty()) {
                println("Le fichier est vide")
                return emptyList()
            }

            val headers = lines.first().split(",")
            val result = mutableListOf<Map<String, String>>()

            for (line in lines.drop(1)) {
                val values = line.split(",")
                val rowMap = headers.zip(values).toMap()
                result.add(rowMap)
            }

            return result
        }

        fun findTripHeadsign(tripId: String, trips: List<Map<String, String>>): String? {
            for (trip in trips) {
                if (trip["trip_id"] == tripId) {
                    return trip["trip_headsign"]
                }
            }
            return null
        }
    }
}