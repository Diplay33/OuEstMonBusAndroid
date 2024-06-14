package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.AllerDestination

@Dao
interface AllerDestinationDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM AllerDestination WHERE input = (:lineId)")
    fun getAllerDestination(lineId: Int): AllerDestination?

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllerDestinations(allerDestinations: List<AllerDestination>)
}