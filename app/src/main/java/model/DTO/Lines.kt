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

        //MARK: - GET

        fun getAllLines(callback: (List<Line>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                callback(lineDAO.getAllLines())
            }
        }

        fun getAllLinesBySection(context: Context, forSchedules: Boolean = false, callback: (List<List<Line>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val lines = (if (forSchedules) lineDAO.getAllLinesForSchedules()
                else
                    lineDAO.getAllLines()).sortedBy { it.index }
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

        fun getLinesBySearchText(searchText: String, forSchedules: Boolean = false, callback: (List<Line>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
                fun CharSequence.unaccent(): String {
                    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                    return REGEX_UNACCENT.replace(temp, "")
                }

                val lines = (if (forSchedules) lineDAO.getAllLinesForSchedules()
                else
                    lineDAO.getAllLines()).sortedBy { it.index }
                callback(lines.filter { line ->
                    line.name.lowercase().unaccent().contains(searchText.trim().lowercase().unaccent())
                })
            }
        }

        fun getChildLineIds(parentId: Int, callback: (List<Int>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch { callback(lineDAO.getChildLineIds(parentId)) }
        }

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
                colorHex = "#DEDEDE",
                isNest = false,
                showSchedules = false,
                parentId = 0,
                createdAt = ""
            )
        }

        //MARK: - SET

        fun insertLines(lines: List<Line>) {
            lineDAO.insertLines(lines)
        }

        fun deleteContent() {
            lineDAO.deleteContent()
        }
    }
}