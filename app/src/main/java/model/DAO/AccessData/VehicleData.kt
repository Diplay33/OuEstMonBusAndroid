package model.DAO.AccessData

class VehicleData {
    companion object {
        val vehicles: List<Map<String, String>> = listOf(
            /*Keolis Bordeaux Métropole*/
            //ALSTOM Citadis TGA402
            mapOf("id" to "2201", "parkId" to "2201", "brand" to "ALSTOM", "model" to "ALSTOM Citadis TGA402", "type" to "TRAMWAY", "operator" to "Keolis Bordeaux Métropole"),
            mapOf("id" to "2202", "parkId" to "2202", "brand" to "ALSTOM", "model" to "ALSTOM Citadis TGA402", "type" to "TRAMWAY", "operator" to "Keolis Bordeaux Métropole"),

            //ALSTOM Citadis TGA302
            mapOf("id" to "2241", "parkId" to "2241", "brand" to "ALSTOM", "model" to "ALSTOM Citadis TGA302", "type" to "TRAMWAY", "operator" to "Keolis Bordeaux Métropole"),
            mapOf("id" to "2245", "parkId" to "2245", "brand" to "ALSTOM", "model" to "ALSTOM Citadis TGA302", "type" to "TRAMWAY", "operator" to "Keolis Bordeaux Métropole"),
            mapOf("id" to "2544", "parkId" to "2544", "brand" to "ALSTOM", "model" to "ALSTOM Citadis TGA302", "type" to "TRAMWAY", "operator" to "Keolis Bordeaux Métropole"),
        )
    }
}