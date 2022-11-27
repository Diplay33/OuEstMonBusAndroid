package model.DTO

import model.DAO.AccessData.DestinationRetourData

class DestinationsRetour {
    companion object {
        fun getDestinationRetourOfLine(lineId: Int): List<List<String>> {
            DestinationRetourData.destinations[lineId]?.let { values ->
                return values
            } ?: run {
                return listOf()
            }
        }
    }
}