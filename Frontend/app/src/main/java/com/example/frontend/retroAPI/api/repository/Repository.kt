package com.example.frontend.retroAPI.api.repository

import com.example.frontend.retroAPI.api.RetrofitInstance
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.model.returnRespondModel
import com.example.frontend.retroAPI.api.model.userInformationModel
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

    suspend fun updateUserInformation(userId: String, name : String, dateofbirth : String,
                                        phonenumber : String, email : String) : returnRespondModel {
        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("name", name)
        jsonObject.put("dateofbirth", dateofbirth)
        jsonObject.put("phonenumber", phonenumber)
        jsonObject.put("email", email)
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        return RetrofitInstance.api.updateUserInformation(requestBody)
    }

}