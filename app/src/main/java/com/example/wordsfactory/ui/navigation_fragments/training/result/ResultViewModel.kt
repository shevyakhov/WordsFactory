package com.example.wordsfactory.ui.navigation_fragments.training.result

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.repository.AppRepository

class ResultViewModel(private val repository: AppRepository) : ViewModel() {


    fun updateDb(list: List<WordEntity>) {
        for (i in list) {
            repository.updateLearningRate(i)
        }
    }

}