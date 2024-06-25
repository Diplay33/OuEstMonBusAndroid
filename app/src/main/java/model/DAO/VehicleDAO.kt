package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.Vehicle

@Dao
interface VehicleDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM Vehicle WHERE id = (:id)")
    fun getVehicle(id: String): Vehicle?

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVehicles(vehicles: List<Vehicle>)

    //MARK: - DELETE

    @Query("DELETE FROM Vehicle")
    fun deleteContent()
}