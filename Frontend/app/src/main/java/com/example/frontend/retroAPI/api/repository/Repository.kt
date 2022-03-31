package com.example.frontend.retroAPI.api.repository

import com.example.frontend.retroAPI.api.RetrofitInstance
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.model.userInformationModel

class Repository {

    suspend fun getUserInformation(userId : String): userInformationModel {
        return RetrofitInstance.api.getUserInformation(userId)
    }

    suspend fun getUserLeaves(userId : String): leaveInformationModel {
        return RetrofitInstance.api.getUserLeaves(userId)
    }

}