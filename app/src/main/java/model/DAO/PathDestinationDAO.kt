package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.PathDestination

@Dao
interface PathDestinationDAO {
    @Query("SELECT * FROM PathDestination WHERE input = (:pathName) AND relatedLine = (:lineId)")
    fun getLineRelatedDestination(pathName: String, lineId: Int): PathDestination?

    @Query("SELECT * FROM PathDestination WHERE input = (:pathName) AND relatedLine IS NULL")
    fun getDestination(pathName: String): PathDestination?
}