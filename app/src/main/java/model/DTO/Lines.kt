package model.DTO

import android.content.Context
import com.example.ouestmonbus.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import model.DAO.LineDAO
import model.preferences_data_store.StoreFavoriteLines

class Lines {
    companion object {
        fun getLinesByGroup(context: Context): ArrayList<ArrayList<Line>> {
            val linesByGroup: ArrayList<ArrayList<Line>> = arrayListOf(
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf()
            )

            LineDAO.getLines().forEach { line ->
                runBlocking(Dispatchers.IO) {
                    if(StoreFavoriteLines(context, line.id.toString()).isFavorite.first()!!) {
                        linesByGroup[0].add(line)
                    }
                }

                when(line.listGroup) {
                    ListGroup.TRAM -> linesByGroup[1].add(line)
                    ListGroup.LIANES -> linesByGroup[2].add(line)
                    ListGroup.LIGNEPRINCIPALE -> linesByGroup[3].add(line)
                    ListGroup.COROL -> linesByGroup[4].add(line)
                    ListGroup.CITEIS -> linesByGroup[5].add(line)
                    ListGroup.FLEXO49 -> linesByGroup[6].add(line)
                    ListGroup.LIGNENUIT -> linesByGroup[7].add(line)
                    ListGroup.LIGNEAUTRES -> linesByGroup[8].add(line)
                    ListGroup.LIGNESPECIALE -> linesByGroup[9].add(line)
                    ListGroup.EVENEMENT -> linesByGroup[10].add(line)
                }
            }

            return linesByGroup
        }

        fun getLinesBySearchText(text: String): List<Line> {
            return LineDAO.getLines().filter { line ->
                line.lineName.lowercase().contains(text.trim().lowercase())
            }
        }

        fun getLine(lineId: String?): Line {
            LineDAO.getLines().map { line ->
                if(line.id.toString() == lineId) {
                    return line
                }
                else if((lineId?.toInt() in 132..164) ||
                    (lineId?.toInt() in 166..182) ||
                    (lineId?.toInt() in 186..199)) {
                    return Line(
                        id = 132,
                        lineName = "Navette Tram",
                        lineImageResource = R.drawable.navette_tram,
                        lineColorResource = R.color.navette_tram,
                        listGroup = ListGroup.LIGNESPECIALE)
                }
            }
            return getEmptyLine()
        }

        private fun getEmptyLine(): Line {
            return Line(
                id =0,
                lineName = "Ligne inconnue",
                lineImageResource = R.drawable.question_mark_box,
                lineColorResource = R.color.light_grey,
                listGroup = ListGroup.LIGNEAUTRES
            )
        }
    }
}