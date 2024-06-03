package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.Line

@Dao
interface LineDAO {
    @Query("SELECT * FROM Line WHERE parentId = ''")
    fun getAllLines(): List<Line>

    @Query("SELECT * FROM Line WHERE showSchedules = 1")
    fun getAllLinesForSchedules(): List<Line>

    @Query("SELECT * FROM Line WHERE id = (:id)")
    fun getLine(id: Int): Line?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLine(line: Line)
}