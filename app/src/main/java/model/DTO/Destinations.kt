package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Destinations {
    companion object {
        private val destinationDAO = MainApplication.appDatabase.getDestinationDAO()

        fun getDestination(input: String, lineId: Int, callback: (Destination?) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                destinationDAO.getLineRelatedDestination(input, lineId)?.let {
                    callback(it)
                    return@launch
                }
                destinationDAO.getDestination(input)?.let {
                    callback(it)
                    return@launch
                }
                callback(null)
            }
        }
    }
}