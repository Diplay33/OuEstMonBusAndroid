package model.DTO

import model.DAO.AccessData.NextSchedulesDestinationData

class NextSchedulesDestinations {
    companion object {
        fun getDestinationFromRaw(lineId: Int, destination: String): List<String> {
            NextSchedulesDestinationData.destinations[destination]?.let { values ->
                when {
                    lineId == 1 && destination == "René Coty" -> return listOf("MERIGNAC", "René Coty")
                    else -> return values
                }
            } ?: run {
                return listOf()
            }
        }
    }
}