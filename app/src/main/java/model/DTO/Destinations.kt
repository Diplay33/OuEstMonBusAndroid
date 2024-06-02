package model.DTO

import model.DAO.AccessData.DestinationData

class Destinations {
    companion object {
        fun getDestinationFromRaw(destination: String, lineId: Int): List<String> {
            when {
                lineId == 1 && destination == "René Coty" ->
                    return listOf("MERIGNAC", "René Coty")
                lineId == 66 && destination == "Les Pins" ->
                    return listOf("MARTIGNAS SUR JALLE", "Les Pins")
                lineId == 34 && destination == "République" ->
                    return listOf("SAINT MÉDARD EN JALLES", "République")
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