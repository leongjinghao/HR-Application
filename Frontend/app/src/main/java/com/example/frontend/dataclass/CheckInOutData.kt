package com.example.frontend.dataclass

data class CheckInOutData(var date: String, var checkInTiming: String, var checkOutTiming: String, var visibility:Boolean = false, var toggled:Boolean = false)
