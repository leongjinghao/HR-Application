package com.example.frontend.retroAPI.api.repository

import android.util.Log
import com.example.frontend.retroAPI.api.RetrofitInstance
import com.example.frontend.retroAPI.api.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class Repository {

    suspend fun getUserInformation(userId : String): userInformationModel {
        return RetrofitInstance.api.getUserInformation(userId)
    }

    suspend fun getUserLeaves(userId : String): leaveInformationModel {
        return RetrofitInstance.api.getUserLeaves(userId)
    }

    suspend fun updateUserInformation(userId: String, name : String, department : String,
                                      dateofbirth : String, phonenumber : String, email : String)
    : returnRespondModel {
        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("name", name)
        jsonObject.put("department", department)
        jsonObject.put("dateofbirth", dateofbirth)
        jsonObject.put("phonenumber", phonenumber)
        jsonObject.put("email", email)
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return RetrofitInstance.api.updateUserInformation(requestBody)
    }

    suspend fun authenticateUserLogin(userEmail: String, userPassword: String): authenticateUserLoginModel {
        return RetrofitInstance.api.authenticateUserLogin(userEmail, userPassword)
    }

    suspend fun updateUserLeavesStatus(userId: String, date: String, status: String)
            : returnRespondModel {
        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("date", date)
        jsonObject.put("status", status)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return RetrofitInstance.api.updateUserLeaveStatus(requestBody)
    }

    suspend fun deleteUserLeaves(userId: String, date : String)
            : returnRespondModel {
        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("date", date)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return RetrofitInstance.api.deleteUserLeaves(requestBody)
    }
}