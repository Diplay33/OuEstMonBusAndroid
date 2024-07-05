package model.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.DTO.Line

@Dao
interface LineDAO {
    //MARK : - SELECT

    @Query("SELECT * FROM Line WHERE parentId IS null")
    fun getAllLines(): List<Line>

    @Query("SELECT * FROM Line WHERE parentId = 9999")
    fun getAllAmetisLines(): List<Line> // to remove

    @Query("SELECT * FROM Line WHERE showSchedules = 1")
    fun getAllLinesForSchedules(): List<Line>

    @Query("SELECT * FROM Line WHERE id = (:id)")
    fun getLine(id: Int): Line?

    @Query("SELECT id FROM Line WHERE parentId = (:parentId)")
    fun getChildLineIds(parentId: Int): List<Int>

    //MARK: - INSERT

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLines(lines: List<Line>)

    //MARK: - DELETE
    @Query("DELETE FROM Line")
    fun deleteContent()
}