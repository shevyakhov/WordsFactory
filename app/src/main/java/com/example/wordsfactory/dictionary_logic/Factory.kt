package com.example.wordsfactory.dictionary_logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/* ViewModel Factory*/
class Factory(private val repository: AppRepository) : ViewModelProvider.Factory {
    private val unknownException = "Unknown ViewModel class"
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }

        throw IllegalArgumentException(unknownException)
    }
}