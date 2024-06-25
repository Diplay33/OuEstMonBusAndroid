package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.RetourDestination

@Dao
interface RetourDestinationDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM RetourDestination WHERE input = (:lineId)")
    fun getRetourDestination(lineId: Int): RetourDestination?

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRetourDestinations(retourDestinations: List<RetourDestination>)

    //MARK: - DELETE

    @Query("DELETE FROM RetourDestination")
    fun deleteContent()
}