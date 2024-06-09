package model.DTO

import com.diplay.ouestmonbus.MainApplication

class PathDestinationsR {
    companion object {
        private val pathDestinationDAO = MainApplication.appDatabase.getPathDestinationDAO()
    }
}