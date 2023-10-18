package model.DTO

import android.content.Context
import com.diplay.ouestmonbus.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import model.DAO.LineDAO
import model.preferences_data_store.StoreFavoriteLines
import java.text.Normalizer

class Lines {
    companion object {
        fun getAllLines(): List<Line> {
            return LineDAO.getLines()
        }

        fun getLinesByGroup(context: Context): ArrayList<ArrayList<Line>> {
            val lines = LineDAO.getLines()
            val listGroupSet = lines.map { it.listGroup }.toSet().toList()
            val linesByGroup: ArrayList<ArrayList<Line>> = ArrayList(listGroupSet.map { arrayListOf() })
            linesByGroup.add(arrayListOf())

            lines.forEach { line ->
                runBlocking(Dispatchers.IO) {
                    if(StoreFavoriteLines(context, line.id.toString()).isFavorite.first()!!) {
                        linesByGroup[0].add(line)
                    }
                }

                for(i in 0 until listGroupSet.count()) {
                    if(line.listGroup == listGroupSet[i]) {
                        linesByGroup[i + 1].add(line)
                    }
                }
            }

            return linesByGroup
        }

        fun getLinesBySearchText(text: String): List<Line> {
            val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
            fun CharSequence.unaccent(): String {
                val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                return REGEX_UNACCENT.replace(temp, "")
            }

            return LineDAO.getLines().filter { line ->
                line.lineName.lowercase().unaccent().contains(text.trim().lowercase().unaccent())
            }
        }

        fun getLine(lineId: String?): Line {
            LineDAO.getLines().map { line ->
                if(line.id.toString() == lineId) {
                    return line
                }
                else if(lineId?.toInt() in 123..198) {
                    return Line(
                        id = 123,
                        lineName = "Navette Tram",
                        lineImageResource = R.drawable.navette_tram,
                        lineColorResource = R.color.navette_tram,
                        listGroup = "Spéciales")
                }
            }
            return getEmptyLine()
        }

        private fun getEmptyLine(): Line {
            return Line(
                id = 0,
                lineName = "Ligne inconnue",
                lineImageResource = R.drawable.question_mark_box,
                lineColorResource = R.color.light_grey,
                listGroup = "Locale 2"
            )
        }
    }
}