package com.example.wordsfactory.ui.navigation_fragments.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wordsfactory.R
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import com.example.wordsfactory.ui.navigation_fragments.dictionary.adapter.WordItem

class DictionaryViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    private var currentWord = WordEntity(
        word = "",
        transcription = "",
        sound = "",
        partOfSpeech = "",
        meanings = listOf()
    )

    fun responseToWordEntity(response: WordResponse): WordEntity {
        return WordEntity(
            word = response.word,
            meanings = getDefinitions(response),
            sound = setAudio(response),
            transcription = setPhonetic(response),
            partOfSpeech = response.meanings[0].partOfSpeech
        )
    }

    private fun setPhonetic(word: WordResponse): String {
        return word.phonetic // don't fix - android studio is wrong
    }

    private fun setAudio(response: WordResponse): String {
        return if (response.phonetics.isNotEmpty()) {
            response.phonetics[0].audio
        } else {
            app.getString(R.string.none)
        }
    }

    private fun getDefinitions(response: WordResponse): List<WordItem> {
        val listing = mutableListOf<WordItem>()
        for (i in response.meanings) {
            val word = WordItem(
                i.definitions[0].definition, i.definitions[0].example
            )
            listing.add(
                word
            )
        }
        return listing
    }

    fun setCurrentWordInfo(word: WordEntity) {
        currentWord.meanings = word.meanings
        currentWord.sound = word.sound
        currentWord.word = word.word
        currentWord.transcription = word.transcription
        currentWord.partOfSpeech = word.partOfSpeech
    }

    fun getCurrentWordName(): String {
        return currentWord.word
    }
    fun getCurrentWordSound(): String {
        return currentWord.sound
    }
    fun getCurrentWordObject(): WordEntity {
        return currentWord
    }
}
