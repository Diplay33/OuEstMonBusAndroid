package model.DAO

import androidx.room.Dao
import androidx.room.Query
import model.DTO.VehicleR

@Dao
interface VehicleRDAO {
    @Query("SELECT * FROM VehicleR WHERE id = (:id)")
    fun getVehicle(id: String): VehicleR?
}