package model.DTO

import com.diplay.ouestmonbus.MainApplication

class AllerDestinations {
    companion object {
        private val allerDestinationDAO = MainApplication.appDatabase.getAllerDestinationDAO()
    }
}