package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.Destination

@Dao
interface DestinationDAO {
    @Query("SELECT * FROM Destination WHERE input = (:input) AND relatedLine = (:lineId)")
    fun getLineRelatedDestination(input: String, lineId: Int): Destination?

    @Query("SELECT * FROM Destination WHERE input = (:input) AND relatedLine IS NULL")
    fun getDestination(input: String): Destination?
}