package com.example.frontend.CheckInOutHistory

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HistoryTable")
data class History(
    @PrimaryKey(autoGenerate = true) val historyID: Int?,
    @ColumnInfo(name = "Date") @NonNull val date: String,
    @ColumnInfo(name = "Day") @NonNull val day: String,
    @ColumnInfo(name = "Type") @NonNull val type: String,
    @ColumnInfo(name = "InTime") @NonNull val checkInTime: String,
    @ColumnInfo(name = "OutTime") @NonNull val checkOutTime: String,
    var visibility:Boolean=false,
    var toggled:Boolean=false
)