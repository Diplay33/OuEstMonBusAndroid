package model.DTO

import android.content.Context
import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.preferences_data_store.StoreFavoriteLines
import java.text.Normalizer

class Lines {
    companion object {
        private val lineDAO = MainApplication.appDatabase.getLineDAO()

        //MARK: - Multiple
        fun getAllLines(callback: (List<Line>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                callback(lineDAO.getAllLinesR())
            }
        }

        fun getAllLinesBySection(context: Context, forSchedules: Boolean = false, callback: (List<List<Line>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val lines = if (forSchedules) lineDAO.getAllLinesRForSchedules()
                else
                    lineDAO.getAllLinesR()
                val listSectionSet = lines.map { it.section }.toSet().toList()
                val linesBySection: ArrayList<ArrayList<Line>> = ArrayList(listSectionSet.map { arrayListOf() })
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

        fun getLinesBySearchText(searchText: String, callback: (List<Line>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
                fun CharSequence.unaccent(): String {
                    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                    return REGEX_UNACCENT.replace(temp, "")
                }

                callback(lineDAO.getAllLinesR().filter { line ->
                    line.name.lowercase().unaccent().contains(searchText.trim().lowercase().unaccent())
                })
            }
        }

        //MARK: - Single
        fun getLine(id: Int, callback: (Line) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                callback(lineDAO.getLine(id) ?: getEmptyLine())
            }
        }

        fun getEmptyLine(): Line {
            return Line(
                network = "",
                id = 0,
                name = "Ligne inconnue",
                type = "",
                index = 0,
                section = 0,
                physicalType = "",
                imageUrl = "",
                colorHex = "#FFFFFF",
                isNest = false,
                showSchedules = false,
                parentId = 0,
                createdAt = ""
            )
        }

        //TO REMOVE
        fun addLine(line: Line) {
            lineDAO.addLine(line)
        }
    }
}