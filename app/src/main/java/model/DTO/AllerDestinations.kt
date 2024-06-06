package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllerDestinations {
    companion object {
        private val allerDestinationDAO = MainApplication.appDatabase.getAllerDestinationDAO()

        fun getAllerDestination(lineId: Int, callback: (AllerDestination?) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                callback(allerDestinationDAO.getAllerDestination(lineId))
            }
        }
    }
}