package com.example.frontend.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel: ViewModel() {
    val historyList:MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    val historyElements: LiveData<List<String>>
        get() = historyList

    fun loadHistoryList(){
        val test = listOf<String>("test", "test","test" ,"test", "test")
        historyList!!.postValue(test)
    }
}