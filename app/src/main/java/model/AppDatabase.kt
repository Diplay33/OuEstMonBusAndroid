package model

import androidx.room.Database
import androidx.room.RoomDatabase
import model.DAO.AllerDestinationDAO
import model.DAO.DestinationDAO
import model.DAO.LineDAO
import model.DAO.NextSchedulesDestinationDAO
import model.DAO.PathDestinationDAO
import model.DAO.RetourDestinationDAO
import model.DAO.VehicleRDAO
import model.DTO.Destination
import model.DTO.Line
import model.DTO.AllerDestination
import model.DTO.NextSchedulesDestination
import model.DTO.PathDestination
import model.DTO.RetourDestination
import model.DTO.VehicleR

@Database(
    entities = [
        Line::class,
        Destination::class,
        AllerDestination::class,
        RetourDestination::class,
        NextSchedulesDestination::class,
        PathDestination::class,
        VehicleR::class
    ],
    version = 12
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val NAME = "APP_DB"
    }
    abstract fun getLineDAO(): LineDAO
    abstract fun getDestinationDAO(): DestinationDAO
    abstract fun getAllerDestinationDAO(): AllerDestinationDAO
    abstract fun getRetourDestinationDAO(): RetourDestinationDAO
    abstract fun getNextSchedulesDestinationDAO(): NextSchedulesDestinationDAO
    abstract fun getPathDestinationDAO(): PathDestinationDAO
    abstract fun getVehicleDAO(): VehicleRDAO
}