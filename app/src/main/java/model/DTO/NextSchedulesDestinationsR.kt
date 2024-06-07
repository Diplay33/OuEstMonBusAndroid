package model.DTO

import com.diplay.ouestmonbus.MainApplication

class NextSchedulesDestinationsR {
    companion object {
        private val nextSchedulesDestinationDAO = MainApplication.appDatabase.getNextSchedulesDestinationDAO()
    }
}