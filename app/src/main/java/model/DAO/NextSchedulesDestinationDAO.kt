package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.NextSchedulesDestination

@Dao
interface NextSchedulesDestinationDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM NextSchedulesDestination WHERE input = (:rawDestination) AND relatedLine = (:lineId)")
    fun getLineRelatedNextSchedulesDestination(rawDestination: String, lineId: Int): NextSchedulesDestination?

    @Query("SELECT * FROM NextSchedulesDestination WHERE input = (:rawDestination) AND relatedLine IS NULL")
    fun getNextSchedulesDestination(rawDestination: String): NextSchedulesDestination?

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNextSchedulesDestinations(nsDestinations: List<NextSchedulesDestination>)

    //MARK: - DELETE

    @Query("DELETE FROM NextSchedulesDestination")
    fun deleteContent()
}