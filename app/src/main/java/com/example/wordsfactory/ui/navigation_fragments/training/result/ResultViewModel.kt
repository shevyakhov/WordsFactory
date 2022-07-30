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

    fun getStatistics(): Pair<Int, Int> {

        val learningRates = repository.getEveryWord()?.map { it.learningRate }
        if (learningRates != null) {
            return learningRates.count { it == 1 } to learningRates.size
        }
        return 0 to 0
    }


}