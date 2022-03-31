package com.example.frontend.retroAPI.api.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.model.userInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import kotlinx.coroutines.launch

class apiViewModel(private val repository: Repository): ViewModel() {

    val userInformationRes: MutableLiveData<userInformationModel> = MutableLiveData()
    val leaveInformationRes: MutableLiveData<leaveInformationModel> = MutableLiveData()

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
}