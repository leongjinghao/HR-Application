package com.example.frontend.CheckInOutHistory

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declare the DAO as a private property in the constructor
// Pass in the DAO instead of the whole database as we only need access to DAO
class HistoryRepository(private val historyDao: HistoryDao) {
    // Retrieve all check in and out history
    val allHistory: Flow<List<History>> = historyDao.getAllHistory()

    // Latest check in or out status, give a default value of "Clock out"
    // to show check in button on default
    var checkInOutStatus: String = "Clock out"

    // Insert new check in and out record into table
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(history: History) {
        historyDao.insert(history)
        checkInOutStatus = history.type
    }

    // Delete all check in and out history from table
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        historyDao.deleteAll()
    }
}