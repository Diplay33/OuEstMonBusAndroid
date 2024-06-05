package model

import androidx.room.Database
import androidx.room.RoomDatabase
import model.DAO.DestinationDAO
import model.DAO.LineDAO
import model.DTO.Destination
import model.DTO.Line

@Database(entities = [Line::class, Destination::class], version = 5)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val NAME = "APP_DB"
    }
    abstract fun getLineDAO(): LineDAO
    abstract fun getDestinationDAO(): DestinationDAO
}