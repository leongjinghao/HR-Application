package com.example.frontend.retroAPI.api.repository

import com.example.frontend.retroAPI.api.RetrofitInstance
import com.example.frontend.retroAPI.api.model.userInformation

class Repository {

    suspend fun retrieveUserInformation(): userInformation {
        return RetrofitInstance.api.retrieveUserInformation()
    }
}