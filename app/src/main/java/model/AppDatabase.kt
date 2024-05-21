package model

import androidx.room.Database
import androidx.room.RoomDatabase
import model.DAO.LineRDAO
import model.DTO.LineR

@Database(entities = [LineR::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val NAME = "APP_DB"
    }
    abstract fun getLineRDAO(): LineRDAO
}