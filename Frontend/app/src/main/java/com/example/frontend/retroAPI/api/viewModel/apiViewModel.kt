package com.example.frontend.retroAPI.api.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.retroAPI.api.model.*
import com.example.frontend.retroAPI.api.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class apiViewModel(private val repository: Repository): ViewModel() {

    val userInformationRes: MutableLiveData<userInformationModel> = MutableLiveData()
    val leaveInformationCalendarRes: MutableLiveData<leaveInformationModel> = MutableLiveData()
    val leaveInformationSummaryRes: MutableLiveData<leaveInformationModel> = MutableLiveData()
    val returnRespondModelRes : MutableLiveData<returnRespondModel> = MutableLiveData()
    val authenticateUserLoginRes : MutableLiveData<authenticateUserLoginModel> = MutableLiveData()
    val createAttendanceInformationRes : MutableLiveData<returnRespondModel> = MutableLiveData()
    val retrieveUserScheduleRes : MutableLiveData<userScheduleModel> = MutableLiveData()
    val currentPasswordRes : MutableLiveData<currentPasswordModel> = MutableLiveData()

    fun getUserInformation(userId : String){
        viewModelScope.launch {
            val response: userInformationModel = repository.getUserInformation(userId)
            userInformationRes.value = response
        }
    }

    fun getUserLeavesSummary(userId : String, condition:String){
        viewModelScope.launch {
            val response: leaveInformationModel = repository.getUserLeaves(userId,condition)
            leaveInformationSummaryRes.value = response
        }
    }

    fun getUserLeavesCalendar(userId : String, condition:String){
        viewModelScope.launch {
            val response: leaveInformationModel = repository.getUserLeaves(userId,condition)
            leaveInformationCalendarRes.value = response
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

    fun updateAttendanceInformation(userId : String, date : String, clockOut : String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.updateAttendanceInformation(userId, date, clockOut)
            createAttendanceInformationRes.value = response
        }
    }

    fun retrieveUserSchedule(userId: String, date: String) {
        viewModelScope.launch {
            val response: userScheduleModel = repository.retrieveUserSchedule(userId, date)
            retrieveUserScheduleRes.value = response
        }
    }

    fun getUserPassword(userId: String) {
        viewModelScope.launch {
            val response: currentPasswordModel = repository.getUserPassword(userId)
            currentPasswordRes.value = response
        }
    }

    fun updateUserPassword(userId: String, newPass : String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.updateUserPassword(userId, newPass)
            returnRespondModelRes.value = response
        }
    }

    fun retrieveUserEmails(userEmail: String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.retrieveUserEmails(userEmail)
            returnRespondModelRes.value = response
        }
    }

    fun sendRecoverEmail(userEmail: String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.sendRecoverEmail(userEmail)
            returnRespondModelRes.value = response
        }
    }

    fun retrieveApprovers(userId : String) {
        viewModelScope.launch {
            val response: userInformationModel = repository.retrieveApprovers(userId)
            userInformationRes.value = response
        }
    }

    fun createUserLeave(userId:String,startEndDate:String,leaveType:String,approver:String,remarks:String) {
        viewModelScope.launch {
            val response: returnRespondModel = repository.createUserLeave(userId,startEndDate,leaveType,approver,remarks)
            returnRespondModelRes.value = response
        }
    }
}