package com.example.wordsfactory.dictionary_logic

import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WordApi {
    @GET("{id}")
    fun retrieveWord(@Path("id") id: String): Call<List<WordResponse>>
}