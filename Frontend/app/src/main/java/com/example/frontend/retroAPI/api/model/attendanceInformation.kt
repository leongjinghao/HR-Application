package com.example.frontend.retroAPI.api.model

data class attendanceInformationModel(
    val result: String
)

data class createAttendanceInfromationField(
    val PK: attendanceInformationInnerObject,
    val SK: attendanceInformationInnerObject,
    val ClockIn: attendanceInformationInnerObject,
    val Location: attendanceInformationInnerObject,
)

data class updateAttendanceInfromationField(
    val PK: attendanceInformationInnerObject,
    val SK: attendanceInformationInnerObject,
    val ClockOut: attendanceInformationInnerObject,
)

data class attendanceInformationInnerObject(
    val S : String
)