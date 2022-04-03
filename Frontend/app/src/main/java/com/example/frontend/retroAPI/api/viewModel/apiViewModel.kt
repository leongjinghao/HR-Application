package com.example.frontend.retroAPI.api.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.retroAPI.api.model.*
import com.example.frontend.retroAPI.api.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class apiViewModel(private val repository: Repository): ViewModel() {

    val userInformationRes: MutableLiveData<userInformationModel> = MutableLiveData()
    val leaveInformationRes: MutableLiveData<leaveInformationModel> = MutableLiveData()
    val returnRespondModelRes : MutableLiveData<returnRespondModel> = MutableLiveData()
    val authenticateUserLoginRes : MutableLiveData<authenticateUserLoginModel> = MutableLiveData()
    val createAttendanceInformationRes : MutableLiveData<returnRespondModel> = MutableLiveData()

    fun getUserInformation(userId : String){
        viewModelScope.launch {
            val response: userInformationModel = repository.getUserInformation(userId)
            userInformationRes.value = response
        }
    }

    fun getUserLeaves(userId : String){
        viewModelScope.launch {
            val response: leaveInformationModel = repository.getUserLeaves(userId)
            leaveInformationRes.value = response
        }
    }

    fun updateUserInformation(userId : String, name : String, department : String,
                              dateofbirth : String, phonenumber : String, email : String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.updateUserInformation(userId,
                                                                                name,
                                                                                department,
                                                                                dateofbirth,
                                                                                phonenumber,
                                                                                email)
            returnRespondModelRes.value = response
        }
    }

    fun authenticateUserLogin(userEmail : String, userPassword : String) {
        viewModelScope.launch {
            val response: authenticateUserLoginModel = repository.authenticateUserLogin(userEmail, userPassword)
            authenticateUserLoginRes.value = response
        }
    }

    fun updateUserLeavesStatus(userId : String, date : String, status : String)
    {
        viewModelScope.launch {
            val response: returnRespondModel = repository.updateUserLeavesStatus(
                userId,
                date,
                status)
            returnRespondModelRes.value = response
        }
    }

    fun deleteUserLeaves(userId : String, date : String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.deleteUserLeaves(
                userId,
                date
            )
            returnRespondModelRes.value = response
        }
    }

    fun createAttendanceInformation(userId : String, date : String, clockIn : String, location : String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.createAttendanceInformation(userId, date, clockIn, location)
            createAttendanceInformationRes.value = response
        }
    }
}