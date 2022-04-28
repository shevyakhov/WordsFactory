package com.example.wordsfactory.dictionary_logic

import com.example.wordsfactory.dictionary_logic.database.UserEntity
import com.example.wordsfactory.dictionary_logic.database.WordDao
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import retrofit2.Call


class AppRepository(
    private val service: NetworkService,
    private val wordDao: WordDao
) {

    fun searchDb(query: String): WordEntity? {
        return wordDao.findById(query)
    }

    fun searchUser(): List<UserEntity>? {
        return wordDao.findUser()
    }

    fun saveUser(userEntity: UserEntity) {
        return wordDao.insertUser(userEntity)
    }

    fun saveToDb(item: WordEntity) {
        wordDao.insert(item)
    }

    fun searchNet(query: String): Call<List<WordResponse>> {
        return service.wordApi.retrieveWord(query)
    }
}