package com.example.wordsfactory.dictionary_logic.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wordsfactory.ui.navigation_fragments.dictionary.adapter.WordItem

@Entity
data class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = WordEntityColumnInfoWord)
    var word: String,
    @ColumnInfo(name = WordEntityColumnInfoTranscription)
    var transcription: String,
    @ColumnInfo(name = WordEntityColumnInfoSound)
    var sound: String,
    @ColumnInfo(name = WordEntityColumnInfoPartOfSpeech)
    var partOfSpeech: String,
    @ColumnInfo(name = WordEntityColumnInfoMeanings)
    var meanings: List<WordItem>
) {
    companion object {
        const val WordEntityColumnInfoWord = "word"
        const val WordEntityColumnInfoTranscription = "transcription"
        const val WordEntityColumnInfoSound = "soundUrl"
        const val WordEntityColumnInfoPartOfSpeech = "partOfSpeech"
        const val WordEntityColumnInfoMeanings = "meanings"
    }
}

data class WordResponse(
    val word: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
    val license: Licence,
    val sourceUrl: List<String>
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>

)

data class Definition(
    val definition: String,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val example: String
)

data class Phonetic(
    val text: String,
    val audio: String,
    val sourceUrl: String? = null,
    val license: Licence? = null
)

data class Licence(val name: String, val url: String)
