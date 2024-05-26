package model.DTO

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.preferences_data_store.StoreFavoriteLines

class LinesR {
    companion object {
        val lineRDAO = MainApplication.appDatabase.getLineRDAO()

        fun getAllLines(): List<LineR> {
            return lineRDAO.getAllLinesR()
        }

        fun getAllLinesBySection(context: Context, callback: (List<List<LineR>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val lines = lineRDAO.getAllLinesR()
                val listSectionSet = lines.map { it.section }.toSet().toList()
                val linesBySection: ArrayList<ArrayList<LineR>> = ArrayList(listSectionSet.map { arrayListOf() })
                linesBySection.add(arrayListOf())

                lines.forEach { line ->
                    runBlocking(Dispatchers.IO) {
                        if(StoreFavoriteLines(context, line.id.toString()).isFavorite.first()!!) {
                            linesBySection[0].add(line)
                        }
                    }

                    for(i in 0 until listSectionSet.count()) {
                        if(line.section == listSectionSet[i]) {
                            linesBySection[i + 1].add(line)
                        }
                    }
                }

                callback(linesBySection)
            }
        }

        //TO REMOVE
        fun addLineR(lineR: LineR) {
            lineRDAO.addLineR(lineR)
        }
    }
}