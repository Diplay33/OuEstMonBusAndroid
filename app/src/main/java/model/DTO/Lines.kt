package model.DTO

import com.example.ouestmonbus.R
import model.DAO.LineDAO

class Lines {
    companion object {
        fun getLinesByGroup(): ArrayList<ArrayList<Line>> {
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
            )

            LineDAO.getLines().forEach { line ->
                when(line.listGroup) {
                    ListGroup.TRAM -> linesByGroup[0].add(line)
                    ListGroup.LIANES -> linesByGroup[1].add(line)
                    ListGroup.LIGNEPRINCIPALE -> linesByGroup[2].add(line)
                    ListGroup.COROL -> linesByGroup[3].add(line)
                    ListGroup.CITEIS -> linesByGroup[4].add(line)
                    ListGroup.FLEXO49 -> linesByGroup[5].add(line)
                    ListGroup.LIGNENUIT -> linesByGroup[6].add(line)
                    ListGroup.LIGNEAUTRES -> linesByGroup[7].add(line)
                    ListGroup.LIGNESPECIALE -> linesByGroup[8].add(line)
                    ListGroup.EVENEMENT -> linesByGroup[9].add(line)
                }
            }

            return linesByGroup
        }

        fun getLinesBySearchText(text: String): List<Line> {
            return LineDAO.getLines().filter { line ->
                line.lineName.lowercase().contains(text.lowercase())
            }
        }

        fun getLine(lineId: String?): Line? {
            LineDAO.getLines().map { line ->
                if(line.id.toString() == lineId) {
                    return line
                }
            }
            return null
        }

        fun getEmptyLine(): Line {
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