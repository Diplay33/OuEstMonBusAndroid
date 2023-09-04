package model.DTO

enum class ListGroup {
    TRAM, LIANES, LIGNEPRINCIPALE, LIGNELOCALE1, LIGNEDIRECTE, TBNIGHT, LIGNELOCALE2, LIGNESPECIALES, EVENEMENT
}

class Line(val id: Int,
           val lineName: String,
           val lineImageResource: Int,
           val lineColorResource: Int,
           val listGroup: ListGroup,
)