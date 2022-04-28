package com.example.wordsfactory.dictionary_logic.repository.app_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.dictionary_logic.repository.AppRepository

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