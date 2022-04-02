package com.example.frontend.retroAPI.api

import com.example.frontend.retroAPI.api.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("update/updateUserInformation/")
    suspend fun updateUserInformation(
        @Body requestBody: RequestBody
    ) : returnRespondModel

    @GET("userLogin/authenticateUserLogin/")
    suspend fun authenticateUserLogin(
        @Query("userEmail") userEmail: String,
        @Query("userPassword") userPassword: String
    ) : authenticateUserLoginModel
}