package model

import androidx.room.Database
import androidx.room.RoomDatabase
import model.DAO.AllerDestinationDAO
import model.DAO.DestinationDAO
import model.DAO.LineDAO
import model.DAO.NextSchedulesDestinationDAO
import model.DAO.RetourDestinationDAO
import model.DTO.Destination
import model.DTO.Line
import model.DTO.AllerDestination
import model.DTO.NextSchedulesDestination
import model.DTO.RetourDestination

@Database(
    entities = [
        Line::class,
        Destination::class,
        AllerDestination::class,
        RetourDestination::class,
        NextSchedulesDestination::class
    ],
    version = 9
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
}