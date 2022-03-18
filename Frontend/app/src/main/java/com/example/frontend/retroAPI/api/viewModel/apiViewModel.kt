package com.example.frontend.retroAPI.api.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.retroAPI.api.model.userInformation
import com.example.frontend.retroAPI.api.repository.Repository
import kotlinx.coroutines.launch

class apiViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<userInformation> = MutableLiveData()

    fun getUserInformation(){
        viewModelScope.launch {
            val response: userInformation = repository.retrieveUserInformation()
            myResponse.value = response
        }
    }
}