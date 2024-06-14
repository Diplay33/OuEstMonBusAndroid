package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.Destination

@Dao
interface DestinationDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM Destination WHERE input = (:input) AND relatedLine = (:lineId)")
    fun getLineRelatedDestination(input: String, lineId: Int): Destination?

    @Query("SELECT * FROM Destination WHERE input = (:input) AND relatedLine IS NULL")
    fun getDestination(input: String): Destination?

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDestinations(destinations: List<Destination>)

    //MARK: - DELETE

    @Query("DELETE FROM Destination")
    fun deleteContent()
}