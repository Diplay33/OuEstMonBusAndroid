package model

import androidx.room.Database
import androidx.room.RoomDatabase
import model.DAO.LineDAO
import model.DTO.Line

@Database(entities = [Line::class], version = 4)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val NAME = "APP_DB"
    }
    abstract fun getLineDAO(): LineDAO
}