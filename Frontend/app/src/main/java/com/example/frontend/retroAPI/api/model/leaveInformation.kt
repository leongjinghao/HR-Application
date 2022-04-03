package com.example.frontend.retroAPI.api.model

data class leaveInformationModel(
    val Items : List<leaveInformationField>?
)

data class leaveInformationField(
    val PK: leaveInformationInnerObject,
    val SK: leaveInformationInnerObject,
    val LeaveType: leaveInformationInnerObject,
    val LeaveStatus: leaveInformationInnerObject,
    val Remarks: leaveInformationInnerObject,
    val Approver: leaveInformationInnerObject,
)

data class leaveInformationInnerObject(
    val S : String
)