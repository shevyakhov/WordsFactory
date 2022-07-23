package com.example.wordsfactory.dictionary_logic.database

import androidx.room.*

@Dao
interface WordDao {
    @Query("SELECT * FROM WordEntity")
    fun getAll(): List<WordEntity>?

    @Query("SELECT * FROM WordEntity WHERE word = :word")
    fun findById(word: String): WordEntity?

    @Query("SELECT * FROM UserEntity")
    fun findUser(): List<UserEntity>?

    @Insert
    fun insertUser(item: UserEntity)

    @Update
    fun updateLearningRate(wordEntity: WordEntity)

    @Insert
    fun insert(item: WordEntity)

    @Delete
    fun delete(item: WordEntity)
}