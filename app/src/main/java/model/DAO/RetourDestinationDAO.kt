package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.RetourDestination

@Dao
interface RetourDestinationDAO {
    @Query("SELECT * FROM RetourDestination WHERE input = (:lineId)")
    fun getRetourDestination(lineId: Int): RetourDestination?
}