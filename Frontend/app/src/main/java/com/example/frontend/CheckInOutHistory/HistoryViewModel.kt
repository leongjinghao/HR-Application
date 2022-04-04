package com.example.frontend.CheckInOutHistory

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    // use LiveData to cache all records of check in and out history
    val allHistory: LiveData<List<History>> = repository.allHistory.asLiveData()

    // Cache for check in and out status
    val checkInOutStatus: String = repository.checkInOutStatus

    // Launch new coroutine to insert data in a non-blocking way
    fun insert(history: History) = viewModelScope.launch {
        repository.insert(history)
    }

    // Delete all records of check in and out history
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun update(checkOutTime:String, checkOutDate:String) = viewModelScope.launch{
        repository.update(checkOutTime,checkOutDate)
    }
}

class HistoryViewModelFactory(private val repository: HistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}