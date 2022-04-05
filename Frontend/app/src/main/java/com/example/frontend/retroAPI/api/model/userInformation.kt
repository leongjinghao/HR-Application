package com.example.frontend.retroAPI.api.model

data class userInformationModel(
    val Items : List<userInformationField>?
)

data class userInformationField(
    val PK: userInformationInnerObject,
    val SK: userInformationInnerObject,
    val EmployeeName: userInformationInnerObject,
    val DOB: userInformationInnerObject,
    val Mobile: userInformationInnerObject,
    val Email: userInformationInnerObject,
    val Department: userInformationInnerObject,
    val Picture: userInformationInnerObject,
    val AL: userInformationInnerObject,
    val MC: userInformationInnerObject,
    val OIL: userInformationInnerObject,
    val Role: userInformationInnerObject
)

data class userInformationInnerObject(
    val S : String
)