package com.example.frontend.retroAPI.api

import com.example.frontend.retroAPI.api.model.userInformation
import retrofit2.http.GET

interface simpleAPI {

    @GET("retrieve/retrieveUserInformation?userId=Ali456")
    suspend fun retrieveUserInformation() : userInformation
}