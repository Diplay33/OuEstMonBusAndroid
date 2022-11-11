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
                else if(lineId?.toInt() == 132 || lineId?.toInt() == 133 || lineId?.toInt() == 134
                    || lineId?.toInt() == 135 || lineId?.toInt() == 136 || lineId?.toInt() == 137
                    || lineId?.toInt() == 138 || lineId?.toInt() == 139 || lineId?.toInt() == 140
                    || lineId?.toInt() == 141 || lineId?.toInt() == 142 || lineId?.toInt() == 143
                    || lineId?.toInt() == 144 || lineId?.toInt() == 145 || lineId?.toInt() == 146
                    || lineId?.toInt() == 147 || lineId?.toInt() == 148 || lineId?.toInt() == 149
                    || lineId?.toInt() == 150 || lineId?.toInt() == 151 || lineId?.toInt() == 152
                    || lineId?.toInt() == 153 || lineId?.toInt() == 154 || lineId?.toInt() == 155
                    || lineId?.toInt() == 156 || lineId?.toInt() == 157 || lineId?.toInt() == 158
                    || lineId?.toInt() == 159 || lineId?.toInt() == 160 || lineId?.toInt() == 161
                    || lineId?.toInt() == 162 || lineId?.toInt() == 163 || lineId?.toInt() == 164
                    || lineId?.toInt() == 166 || lineId?.toInt() == 167 || lineId?.toInt() == 168
                    || lineId?.toInt() == 169 || lineId?.toInt() == 170 || lineId?.toInt() == 171
                    || lineId?.toInt() == 172 || lineId?.toInt() == 173 || lineId?.toInt() == 174
                    || lineId?.toInt() == 175 || lineId?.toInt() == 176 || lineId?.toInt() == 177
                    || lineId?.toInt() == 178 || lineId?.toInt() == 179 || lineId?.toInt() == 180
                    || lineId?.toInt() == 181 || lineId?.toInt() == 182 || lineId?.toInt() == 186
                    || lineId?.toInt() == 187 || lineId?.toInt() == 188 || lineId?.toInt() == 189
                    || lineId?.toInt() == 190 || lineId?.toInt() == 191 || lineId?.toInt() == 192
                    || lineId?.toInt() == 193 || lineId?.toInt() == 194 || lineId?.toInt() == 195
                    || lineId?.toInt() == 196 || lineId?.toInt() == 197 || lineId?.toInt() == 198
                    || lineId?.toInt() == 199) {
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