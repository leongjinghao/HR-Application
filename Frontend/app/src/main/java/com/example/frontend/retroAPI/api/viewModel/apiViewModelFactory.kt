package com.example.frontend.retroAPI.api.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frontend.retroAPI.api.repository.Repository

class apiViewModelFactory(
    private val repository: Repository
):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return apiViewModel(repository) as T
    }
}