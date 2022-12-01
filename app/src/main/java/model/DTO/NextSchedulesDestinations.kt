package model.DTO

import model.DAO.AccessData.NextSchedulesDestinationData

class NextSchedulesDestinations {
    companion object {
        fun getDestinationFromRaw(destination: String): List<String> {
            NextSchedulesDestinationData.destinations[destination]?.let { values ->
                return values
            } ?: run {
                return listOf()
            }
        }
    }
}