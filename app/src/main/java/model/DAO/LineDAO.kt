package model.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.Line

@Dao
interface LineDAO {
    @Query("SELECT * FROM Line WHERE parentId IS null")
    fun getAllLines(): List<Line>

    @Query("SELECT * FROM Line WHERE showSchedules = 1")
    fun getAllLinesForSchedules(): List<Line>

    @Query("SELECT * FROM Line WHERE id = (:id)")
    fun getLine(id: Int): Line?

    @Query("SELECT id FROM Line WHERE parentId = (:parentId)")
    fun getChildLineIds(parentId: Int): List<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLine(line: Line)
}