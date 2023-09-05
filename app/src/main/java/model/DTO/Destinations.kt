package model.DTO

import model.DAO.AccessData.DestinationData

class Destinations {
    companion object {
        fun getDestinationFromRaw(destination: String, lineId: Int): List<String> {
            when {
                lineId == 1 && destination == "René Coty" ->
                    return listOf("MERIGNAC", "René Coty")
                lineId == 63 && destination == "Les Pins" ->
                    return listOf("MARTIGNAS SUR JALLE", "Les Pins")
                else ->
                    DestinationData.destinations[destination]?.let { values ->
                        return values
                    } ?: run {
                        return listOf("", destination)
                    }
            }
        }
    }
}