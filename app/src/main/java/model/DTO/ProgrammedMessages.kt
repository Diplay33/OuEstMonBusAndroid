package model.DTO

import model.DAO.ProgrammedMessageDAO

class ProgrammedMessages {
    companion object {
        fun getNumberOfMessagesByLine(lineId: String, callback: (Int) -> Unit) {
            ProgrammedMessageDAO.getProgrammedMessagesByLine(lineId) { programmedMessages ->
                callback(programmedMessages.size)
            }
        }
    }
}