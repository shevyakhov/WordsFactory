package com.example.wordsfactory.dictionary_logic

import android.content.Context
import android.util.Log
import com.example.wordsfactory.dictionary_logic.database.AppDatabase
import com.example.wordsfactory.dictionary_logic.database.WordDao
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import java.util.concurrent.Executors


class AppRepository(
    private val service: NetworkService,
    private val dao: WordDao
) {

    fun searchDb(query: String): WordEntity {
        return dao.findById(query)
    }
    fun saveToDb(item: WordEntity) {
        dao.insert(item)
    }
    fun searchNet(query: String): Call<List<WordResponse>> {
        return service.tmdbApi.retrieveWord(query)
    }
}