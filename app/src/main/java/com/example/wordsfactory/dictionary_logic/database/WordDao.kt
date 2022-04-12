package com.example.wordsfactory.dictionary_logic.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM WordEntity")
    fun getAll(): List<WordEntity>
    @Query("SELECT * FROM WordEntity WHERE word = :word")
    fun findById(word:String): WordEntity
    @Insert
    fun insert( item: WordEntity)
    @Delete
    fun delete(item: WordEntity)
}