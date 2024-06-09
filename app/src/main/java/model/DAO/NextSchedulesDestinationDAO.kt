package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.NextSchedulesDestination

@Dao
interface NextSchedulesDestinationDAO {
    @Query("SELECT * FROM NextSchedulesDestination WHERE input = (:rawDestination) AND relatedLine = (:lineId)")
    fun getLineRelatedNextSchedulesDestination(rawDestination: String, lineId: Int): NextSchedulesDestination?

    @Query("SELECT * FROM NextSchedulesDestination WHERE input = (:rawDestination) AND relatedLine IS NULL")
    fun getNextSchedulesDestination(rawDestination: String): NextSchedulesDestination?
}