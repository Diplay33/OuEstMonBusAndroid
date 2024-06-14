package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllerDestinations {
    companion object {
        private val allerDestinationDAO = MainApplication.appDatabase.getAllerDestinationDAO()

        //MARK: - GET

        fun getListOfDestinations(lineId: Int, callback: (List<List<String>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                allerDestinationDAO.getAllerDestination(lineId)?.let { allerDestination ->
                    val destinationsToReturn: MutableList<MutableList<String>> = mutableListOf(
                        mutableListOf(allerDestination.city1, allerDestination.destination1)
                    )

                    allerDestination.city2?.let { destinationsToReturn.add(mutableListOf(it)) }
                    allerDestination.destination2?.let { destinationsToReturn[1].add(it) }

                    allerDestination.city3?.let { destinationsToReturn.add(mutableListOf(it)) }
                    allerDestination.destination3?.let { destinationsToReturn[2].add(it) }

                    callback(destinationsToReturn)
                    return@launch
                }
                callback(listOf())
            }
        }

        //MARK: - SET

        fun insertAllerDestinations(allerDestinations: List<AllerDestination>) {
            allerDestinationDAO.insertAllerDestinations(allerDestinations)
        }
    }
}