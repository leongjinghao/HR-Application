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
        @Query("userId") userId: String,
        @Query("condition") condition: String
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

    @POST("update/updateUserLeavesStatus/")
    suspend fun updateUserLeaveStatus(
        @Body requestBody: RequestBody
    ) : returnRespondModel

    @POST("delete/deleteUserLeaves/")
    suspend fun deleteUserLeaves(
        @Body requestBody: RequestBody
    ) : returnRespondModel

    @POST("create/createAttendanceInfo/")
    suspend fun createAttendanceInfomation(
        @Body requestBody: RequestBody
    ) : returnRespondModel

    @POST("update/updateAttendanceInfo/")
    suspend fun updateAttendanceInfomation(
        @Body requestBody: RequestBody
    ) : returnRespondModel

    @GET("retrieve/retrieveUserSchedule/")
    suspend fun retrieveUserSchedule(
        @Query("userId") userId: String,
        @Query("date") date: String
    ) : userScheduleModel

    @GET("retrieve/retrievePassword/")
    suspend fun getUserPassword(
        @Query("UserId") userId: String
    ) : currentPasswordModel

    @POST("update/updatePassword/")
    suspend fun updateUserPassword(
        @Body requestBody: RequestBody
    ) : returnRespondModel

    @GET("retrieve/retrieveUserEmails/")
    suspend fun retrieveUserEmails(
        @Query("userEmail") userEmail: String
    ) : returnRespondModel

    @POST("utility/sesSendEmail/")
    suspend fun sendRecoverEmail(
        @Body requestBody: RequestBody
    ) : returnRespondModel
}