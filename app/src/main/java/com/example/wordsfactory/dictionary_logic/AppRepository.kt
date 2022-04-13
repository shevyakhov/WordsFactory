package com.example.wordsfactory.dictionary_logic

import com.example.wordsfactory.dictionary_logic.database.WordDao
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import retrofit2.Call


class AppRepository(
    private val service: NetworkService,
    private val dao: WordDao
) {

    fun searchDb(query: String): WordEntity? {
        return dao.findById(query)
    }
    fun saveToDb(item: WordEntity) {
        dao.insert(item)
    }
    fun searchNet(query: String): Call<List<WordResponse>> {
        return service.tmdbApi.retrieveWord(query)
    }
}