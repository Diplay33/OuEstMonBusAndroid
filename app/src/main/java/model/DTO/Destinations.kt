package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Destinations {
    companion object {
        private val destinationDAO = MainApplication.appDatabase.getDestinationDAO()

        //MARK: - GET

        fun getDestination(input: String, lineId: Int): Destination? {
            destinationDAO.getLineRelatedDestination(input, lineId)?.let {
                return it
            }
            return destinationDAO.getDestination(input)
        }

        //MARK: - SET

        fun insertDestinations(destinations: List<Destination>) {
            destinationDAO.insertDestinations(destinations)
        }

        fun deleteContent() {
            destinationDAO.deleteContent()
        }
    }
}