package com.example.frontend.CheckInOutHistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    // Retrieve all records of check in and out history
    @Query("SELECT * FROM HistoryTable")
    fun getAllHistory(): Flow<List<History>>

    // Insert new check in and out record into table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

    // Delete all check in and out history from table
    @Query("DELETE FROM HistoryTable")
    suspend fun deleteAll()
}