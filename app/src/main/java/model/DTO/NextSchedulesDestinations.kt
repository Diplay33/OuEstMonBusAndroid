package model.DTO

import model.DAO.AccessData.NextSchedulesDestinationData

class NextSchedulesDestinations {
    companion object {
        fun getDestinationFromRaw(lineId: Int, destination: String): List<String> {
            NextSchedulesDestinationData.destinations[destination.trim()]?.let { values ->
                when {
                    lineId == 1 && destination == "René Coty" -> return listOf("MERIGNAC", "René Coty")
                    lineId == 34 && destination == "République" -> return listOf("SAINT MÉDARD EN JALLES", "République")
                    else -> return values
                }
            } ?: run {
                return listOf("", "Destination inconnue")
            }
        }
    }
}