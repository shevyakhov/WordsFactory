package com.example.wordsfactory.ui.navigation_fragments.dictionary

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.lifecycle.AndroidViewModel
import com.example.wordsfactory.R
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import com.example.wordsfactory.ui.navigation_fragments.dictionary.adapter.WordItem
import com.example.wordsfactory.widget.StatsWidget

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
        Log.e(response.word, response.toString())
        return WordEntity(
            word = response.word,
            meanings = getDefinitions(response),
            sound = setAudio(response),
            transcription = setPhonetic(response),
            partOfSpeech = setPartOfSpeech(response)
        )
    }

    private fun setPartOfSpeech(response: WordResponse): String {
        val parts = ArrayList<String>()
        for (i in response.meanings) {
            parts.add(i.partOfSpeech)
        }
        return parts.joinToString("/")
    }

    private fun setPhonetic(word: WordResponse): String {
        /**
         * must check if (word.phonetic == null) and if (phonetics.text != null)
         * */
        var phonetic = ""
        if (word.phonetic == null) {
            for (i in word.phonetics) {
                if (i.text != null) {
                    phonetic = i.text
                    return phonetic
                }
            }
        } else {
            phonetic = word.phonetic
        }
        return phonetic
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
            for (j in i.definitions) {
                val word = WordItem(
                    j.definition, j.example
                )
                listing.add(
                    word
                )
            }
        }
        return listing
    }

    fun addToSharedPrefs(all: Int) {
        val sharedPreferences = app.getSharedPreferences(
            "SHARED_PREFS",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences?.edit()
        editor?.putInt("SHARED_PREFS_ALL", all)
        editor?.apply()
        updateWidget(all)
    }

    private fun updateWidget(howMany: Int) {
        val remoteViews = RemoteViews(app.packageName, R.layout.stats_widget)
        val dictionaryWordsText = "$howMany ${app.getString(R.string.widgetWordsText)}"

        remoteViews.setTextViewText(R.id.appwidget_text_my_dictionary_stats, dictionaryWordsText)

        val appWidget = ComponentName(app, StatsWidget::class.java)
        val appWidgetManager = AppWidgetManager.getInstance(app)
        appWidgetManager.updateAppWidget(appWidget, remoteViews)
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
