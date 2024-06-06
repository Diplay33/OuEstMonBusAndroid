package model

import androidx.room.Database
import androidx.room.RoomDatabase
import model.DAO.AllerDestinationDAO
import model.DAO.DestinationDAO
import model.DAO.LineDAO
import model.DTO.Destination
import model.DTO.Line
import model.DTO.AllerDestination

@Database(
    entities = [
        Line::class,
        Destination::class,
        AllerDestination::class
    ],
    version = 6
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val NAME = "APP_DB"
    }
    abstract fun getLineDAO(): LineDAO
    abstract fun getDestinationDAO(): DestinationDAO
    abstract fun getAllerDestinationDAO(): AllerDestinationDAO
}