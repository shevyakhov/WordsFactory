package com.example.wordsfactory.dictionary_logic.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wordsfactory.adapters.WordItem

@Entity
data class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    var word: String,
    @ColumnInfo(name = "transcription")
    var transcription: String,
    @ColumnInfo(name = "soundUrl")
    var sound: String,
    @ColumnInfo(name = "partOfSpeech")
    var partOfSpeech: String,
    @ColumnInfo
    var meanings: List<WordItem>
)

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
