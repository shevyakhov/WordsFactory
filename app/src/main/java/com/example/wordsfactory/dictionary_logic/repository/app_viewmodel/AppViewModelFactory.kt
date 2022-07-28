package com.example.wordsfactory.dictionary_logic.repository.app_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.dictionary_logic.repository.AppRepository
import com.example.wordsfactory.ui.navigation_fragments.training.result.ResultViewModel

/* ViewModel Factory*/
class AppViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    private val unknownException = "Unknown ViewModel class"
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }

        throw IllegalArgumentException(unknownException)
    }
}

class ResultViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    private val unknownException = "Unknown ViewModel class"
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(repository) as T
        }

        throw IllegalArgumentException(unknownException)
    }
}