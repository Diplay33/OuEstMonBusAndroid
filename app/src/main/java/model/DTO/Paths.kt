package model.DTO

import model.DAO.PathDAO

class Paths {
    companion object {
        fun getOrderedPathsByLine(lineId: Int, callback: (List<List<Path>>) -> Unit) {
            PathDAO.getPathsByLine(lineId) { paths ->
                val pathsAller: MutableList<Path> = mutableListOf()
                val pathsRetour: MutableList<Path> = mutableListOf()

                paths.forEach { path ->
                    if(path.direction == "ALLER") {
                        pathsAller.add(path)
                    }
                    else {
                        pathsRetour.add(path)
                    }
                }
                if(pathsRetour.isEmpty()) {
                    callback(listOf(pathsAller))
                }
                else {
                    callback(listOf(pathsAller, pathsRetour))
                }
            }
        }
    }
}