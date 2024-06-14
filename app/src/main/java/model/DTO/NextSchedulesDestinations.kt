package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NextSchedulesDestinations {
    companion object {
        private val nextSchedulesDestinationDAO = MainApplication.appDatabase.getNextSchedulesDestinationDAO()

        //MARK: - GET

        fun getDestination(rawDestination: String, lineId: Int, callback: (NextSchedulesDestination?) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                nextSchedulesDestinationDAO.getLineRelatedNextSchedulesDestination(rawDestination, lineId)?.let {
                    callback(it)
                    return@launch
                }
                nextSchedulesDestinationDAO.getNextSchedulesDestination(rawDestination)?.let {
                    callback(it)
                    return@launch
                }
                callback(null)
            }
        }

        //MARK: - SET

        fun insertNextSchedulesDestinations(nsDestinations: List<NextSchedulesDestination>) {
            nextSchedulesDestinationDAO.insertNextSchedulesDestinations(nsDestinations)
        }

        fun deleteContent() {
            nextSchedulesDestinationDAO.deleteContent()
        }
    }
}