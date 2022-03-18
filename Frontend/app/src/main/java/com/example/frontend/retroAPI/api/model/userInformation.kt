package com.example.frontend.retroAPI.api.model

data class userInformation(
    val Items : List<field>?
)

data class field(
    val PK: innerObject,
    val SK: innerObject,
    val Name: innerObject,
    val DOB: innerObject,
    val Mobile: innerObject,
    val Email: innerObject,
    val Department: innerObject,
    val Picture: innerObject,
    val AL: innerObject,
    val MC: innerObject,
    val OIL: innerObject
)

data class innerObject(
    val S : String
)