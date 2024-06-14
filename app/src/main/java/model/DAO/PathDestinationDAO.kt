package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.PathDestination

@Dao
interface PathDestinationDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM PathDestination WHERE input = (:pathName) AND relatedLine = (:lineId)")
    fun getLineRelatedDestination(pathName: String, lineId: Int): PathDestination?

    @Query("SELECT * FROM PathDestination WHERE input = (:pathName) AND relatedLine IS NULL")
    fun getDestination(pathName: String): PathDestination?

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPathDestinations(pathDestinations: List<PathDestination>)

    //MARK: - DELETE

    @Query("DELETE FROM PathDestination")
    fun deleteContent()
}