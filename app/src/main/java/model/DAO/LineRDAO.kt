package model.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.LineR

@Dao
interface LineRDAO {
    @Query("SELECT * FROM LineR")
    fun getAllLinesR(): List<LineR>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLineR(lineR: LineR)
}