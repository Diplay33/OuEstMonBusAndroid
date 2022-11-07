package model.DTO

import model.DAO.AccessData.DestinationData

class Destinations {
    companion object {
        fun getDestinationFromRaw(destination: String): List<String> {
            DestinationData.destinations[destination]?.let { values ->
                return values
            } ?: run {
                return listOf("", destination)
            }
        }
    }
}