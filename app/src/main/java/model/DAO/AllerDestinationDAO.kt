package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.AllerDestination

@Dao
interface AllerDestinationDAO {
    @Query("SELECT * FROM AllerDestination WHERE input = (:lineId)")
    fun getAllerDestination(lineId: Int): AllerDestination?
}