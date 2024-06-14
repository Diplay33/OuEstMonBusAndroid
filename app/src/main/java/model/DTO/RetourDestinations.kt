package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RetourDestinations {
    companion object {
        private val retourDestinationDAO = MainApplication.appDatabase.getRetourDestinationDAO()

        //MARK: - GET

        fun getListOfDestinations(lineId: Int, callback: (List<List<String>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                retourDestinationDAO.getRetourDestination(lineId)?.let { retourDestination ->
                    val destinationsToReturn: MutableList<MutableList<String>> = mutableListOf(
                        mutableListOf(retourDestination.city1, retourDestination.destination1)
                    )

                    retourDestination.city2?.let { destinationsToReturn.add(mutableListOf(it)) }
                    retourDestination.destination2?.let { destinationsToReturn[1].add(it) }

                    retourDestination.city3?.let { destinationsToReturn.add(mutableListOf(it)) }
                    retourDestination.destination3?.let { destinationsToReturn[2].add(it) }

                    callback(destinationsToReturn)
                    return@launch
                }
                callback(listOf())
            }
        }

        //MARK: - SET

        fun insertRetourDestinations(retourDestinations: List<RetourDestination>) {
            retourDestinationDAO.insertRetourDestinations(retourDestinations)
        }
    }
}