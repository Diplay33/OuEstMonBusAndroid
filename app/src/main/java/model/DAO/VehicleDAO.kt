package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.Vehicle

@Dao
interface VehicleDAO {
    @Query("SELECT * FROM Vehicle WHERE id = (:id)")
    fun getVehicle(id: String): Vehicle?
}