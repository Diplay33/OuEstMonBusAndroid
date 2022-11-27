package model.DTO

import model.DAO.AccessData.DestinationAllerData

class DestinationsAller {
    companion object {
        fun getDestinationAllerOfLine(lineId: Int): List<List<String>> {
            DestinationAllerData.destinations[lineId]?.let { values ->
                return values
            } ?: run {
                return listOf()
            }
        }
    }
}