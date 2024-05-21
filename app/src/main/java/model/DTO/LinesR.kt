package model.DTO

import androidx.lifecycle.LiveData
import com.diplay.ouestmonbus.MainApplication

class LinesR {
    companion object {
        val lineRDAO = MainApplication.appDatabase.getLineRDAO()

        fun getAllLines(): LiveData<List<LineR>> {
            return lineRDAO.getAllLinesR()
        }

        //TO REMOVE
        fun addLineR(lineR: LineR) {
            lineRDAO.addLineR(lineR)
        }
    }
}