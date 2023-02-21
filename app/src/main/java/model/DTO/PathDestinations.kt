package model.DTO

import model.DAO.AccessData.PathDestinationData

class PathDestinations {
    companion object {
        fun getDestinationFromPathName(destination: String): List<String> {
            val reversedName = destination.reversed()
            var processedString = ""
            for(i in reversedName.indices) {
                processedString += reversedName[i]
                if(reversedName[i] == ' ' && reversedName[i + 1] == '-') { break }
            }

            PathDestinationData.destinations[processedString.trim().reversed()]?.let { values ->
                return values
            } ?: run {
                return listOf()
            }
        }
    }
}