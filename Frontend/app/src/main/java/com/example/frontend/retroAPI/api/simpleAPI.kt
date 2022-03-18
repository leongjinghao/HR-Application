package com.example.frontend.retroAPI.api

import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.model.userInformationModel
import retrofit2.http.GET
import retrofit2.http.Query

interface simpleAPI {

    @GET("retrieve/retrieveUserInformation/")
    suspend fun getUserInformation(
        @Query("userId") userId: String
    ) : userInformationModel

    @GET("retrieve/retrieveUserLeaves/")
    suspend fun getUserLeaves(
        @Query("userId") userId: String
    ) : leaveInformationModel
}