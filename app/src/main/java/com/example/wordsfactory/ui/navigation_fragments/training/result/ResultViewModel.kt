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

    fun getLearnedCount(): Pair<Int, Int> {
        var learned = 0
        val list = getAllWordsFromDB()
        if (list != null) {
            for (i in list) {
                if (i.learningRate == 1) {
                    learned++
                }
            }
            return learned to list.size
        }
        return 0 to 0
    }

    fun getWordLr(): String {
        val result = ArrayList<Pair<String, Int>>()
        val list = getAllWordsFromDB()
        if (list != null) {
            for (i in list) {
                result.add(i.word to i.learningRate)
            }
        }
        return result.joinToString(" | ")
    }


    private fun getAllWordsFromDB(): List<WordEntity>? {
        return repository.getEveryWord()
    }
}