package model.DAO

import model.DAO.AccessData.LineData
import model.DTO.Line

class LineDAO {
     companion object {
         fun getLines(): ArrayList<Line> {
             return LineData.lines
         }
     }
}