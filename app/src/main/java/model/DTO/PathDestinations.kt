package model.DTO

import model.DAO.AccessData.PathDestinationData

class PathDestinations {
    companion object {
        fun getDestinationFromPathName(lineId: Int, destination: String): List<String> {
            val reversedName = destination.reversed()
            var processedString = ""
            for(i in reversedName.indices) {
                processedString += reversedName[i]
                if(reversedName[i] == ' ' && reversedName[i + 1] == '-') { break }
            }
            val finalString = processedString.trim().reversed()

            when {
                lineId == 10 && finalString == "Hippodrome" -> {
                    return listOf("EYSINES", "Hippodrome", "")
                }
                lineId == 13 && finalString == "Les Pins" -> {
                    return listOf("MERIGNAC", "Les Pins", "")
                }
                else -> {
                    PathDestinationData.destinations[finalString]?.let { values ->
                        return values
                    } ?: run {
                        return listOf()
                    }
                }
            }
        }
    }
}