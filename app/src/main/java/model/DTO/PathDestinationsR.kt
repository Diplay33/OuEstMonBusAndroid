package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PathDestinationsR {
    companion object {
        private val pathDestinationDAO = MainApplication.appDatabase.getPathDestinationDAO()

        fun getDestination(pathName: String, lineId: Int, callback: (PathDestination?) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val reversedName = pathName.reversed()
                var processedString = ""
                for(i in reversedName.indices) {
                    processedString += reversedName[i]
                    if(reversedName[i] == ' ' && reversedName[i + 1] == '-') { break }
                }
                val finalString = processedString.trim().reversed()

                pathDestinationDAO.getLineRelatedDestination(finalString, lineId)?.let {
                    callback(it)
                    return@launch
                }
                pathDestinationDAO.getDestination(finalString)?.let {
                    callback(it)
                    return@launch
                }
                callback(null)
            }
        }
    }
}