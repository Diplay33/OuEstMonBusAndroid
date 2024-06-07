package model.DTO

import com.diplay.ouestmonbus.MainApplication

class RetourDestinations {
    companion object {
        private val retourDestinationDAO = MainApplication.appDatabase.getRetourDestinationDAO()
    }
}