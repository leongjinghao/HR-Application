package com.example.frontend

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "HistoryTable")
data class History(
    @ColumnInfo(name = "Date") @NonNull val date: String,
    @ColumnInfo(name = "Day") @NonNull val day: String,
    @ColumnInfo(name = "Type") @NonNull val type: String,
    @ColumnInfo(name = "Time") @NonNull val time: String
)