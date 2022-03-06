package com.example.frontend

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HRApplication : Application() {
    // Not required to cancel this scope as it will be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // lazy keyword ensures that the check in and out history database and repository
    // only created when needed rather than on application starts
    val database by lazy { HistoryRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { HistoryRepository(database.HistoryDao()) }
}