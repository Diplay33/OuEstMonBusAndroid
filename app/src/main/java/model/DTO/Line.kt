package model.DTO

enum class ListGroup {
    TRAM, LIANES, LIGNEPRINCIPALE, COROL, CITEIS, FLEXO49, LIGNENUIT, LIGNEAUTRES, LIGNESPECIALE, EVENEMENT
}

class Line(val id: Int,
           val lineName: String,
           val lineImageResource: Int,
           val lineColorResource: Int,
           val listGroup: ListGroup,
)