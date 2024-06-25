package model.DAO

import model.CallAPI
import model.DTO.ProgrammedMessage
import model.DTO.Severity
import org.json.JSONObject

class ProgrammedMessageDAO {
    companion object {
        fun getProgrammedMessagesByLine(lineId: String, callback: (ArrayList<ProgrammedMessage>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson/features/sv_messa_a?key=0234ABEFGH&filter={\"rs_sv_ligne_a\":$lineId}") { responseBody ->
                try {
                    val programmedMessages: ArrayList<ProgrammedMessage> = arrayListOf()
                    val welcomeObject = JSONObject(responseBody)
                    val featuresJSONArray = welcomeObject.getJSONArray("features")

                    try {
                        for(i in 0 until featuresJSONArray.length()) {
                            val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                            val propertiesJSONObject =
                                featuresJSONObject.getJSONObject("properties")
                            programmedMessages.add(
                                ProgrammedMessage(
                                    id = propertiesJSONObject.getInt("gid"),
                                    title = propertiesJSONObject.getString("titre"),
                                    bodyMessage = propertiesJSONObject.getString("message"),
                                    lineId = propertiesJSONObject.getInt("rs_sv_ligne_a"),
                                    lastUpdatedRaw = propertiesJSONObject.getString("mdate"),
                                    severity = if (propertiesJSONObject.getString("severite").contains("1"))
                                        Severity.LOW
                                    else
                                        if (propertiesJSONObject.getString("severite").contains("2"))
                                            Severity.MIDDLE
                                        else
                                            Severity.IMPORTANT
                                )
                            )
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process: $e")
                    }

                    callback(programmedMessages)
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }
            }
        }

        fun getProgrammedMessages(callback: (ArrayList<ProgrammedMessage>) -> Unit) {
            CallAPI.run("https://data.bordeaux-metropole.fr/geojson?key=0234ABEFGH&typename=sv_messa_a") { responseBody ->
                try {
                    val programmedMessages: ArrayList<ProgrammedMessage> = arrayListOf()
                    val welcomeObject = JSONObject(responseBody)
                    val featuresJSONArray = welcomeObject.getJSONArray("features")

                    try {
                        for(i in 0 until featuresJSONArray.length()) {
                            val featuresJSONObject = featuresJSONArray.getJSONObject(i)
                            val propertiesJSONObject =
                                featuresJSONObject.getJSONObject("properties")
                            programmedMessages.add(
                                ProgrammedMessage(
                                    id = propertiesJSONObject.getInt("gid"),
                                    title = propertiesJSONObject.getString("titre"),
                                    bodyMessage = propertiesJSONObject.getString("message"),
                                    lineId = propertiesJSONObject.getInt("rs_sv_ligne_a"),
                                    lastUpdatedRaw = propertiesJSONObject.getString("mdate"),
                                    severity = if (propertiesJSONObject.getString("severite").contains("1"))
                                        Severity.LOW
                                    else
                                        if (propertiesJSONObject.getString("severite").contains("2"))
                                            Severity.MIDDLE
                                        else
                                            Severity.IMPORTANT
                                )
                            )
                        }
                    }
                    catch(e: Exception) {
                        println("Error during decoding process: $e")
                    }

                    callback(programmedMessages)
                }
                catch(e: Exception) {
                    println("Error during decoding process: $e")
                }
            }
        }
    }
}