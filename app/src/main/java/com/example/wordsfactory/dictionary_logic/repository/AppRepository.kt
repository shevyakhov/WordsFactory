package com.example.wordsfactory.dictionary_logic.repository

import com.example.wordsfactory.dictionary_logic.database.UserEntity
import com.example.wordsfactory.dictionary_logic.database.WordDao
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import com.example.wordsfactory.dictionary_logic.repository.retrofit.NetworkService
import retrofit2.Call


class AppRepository(
    private val service: NetworkService,
    private val wordDao: WordDao
) {

    fun getEveryWord(): List<WordEntity>? {
        return wordDao.getAll()
    }

    fun searchDb(query: String): WordEntity? {
        return wordDao.findById(query)
    }

    fun searchUser(): List<UserEntity>? {
        return wordDao.findUser()
    }

    fun saveUser(userEntity: UserEntity) {
        return wordDao.insertUser(userEntity)
    }

    fun updateLearningRate(wordEntity: WordEntity) {
        wordDao.updateLearningRate(wordEntity)
    }

    fun saveToDb(item: WordEntity) {
        wordDao.insert(item)
    }

    fun searchNet(query: String): Call<List<WordResponse>> {
        return service.wordApi.retrieveWord(query)
    }
}