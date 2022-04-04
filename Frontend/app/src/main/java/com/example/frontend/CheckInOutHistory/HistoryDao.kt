package com.example.frontend.CheckInOutHistory

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    // Retrieve all records of check in and out history
    @Query("SELECT * FROM HistoryTable")
    fun getAllHistory(): Flow<List<History>>

    // Insert new check in and out record into table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

    // Update Check Out Time
    @Query("UPDATE HistoryTable SET OutTime=:checkOutTime where Date=:checkOutDate")
    suspend fun update(checkOutTime:String, checkOutDate:String)

    // Delete all check in and out history from table
    @Query("DELETE FROM HistoryTable")
    suspend fun deleteAll()
}