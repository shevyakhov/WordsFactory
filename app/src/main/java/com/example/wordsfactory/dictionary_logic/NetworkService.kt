package com.example.wordsfactory.dictionary_logic

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor() {
    private val mRetrofit: Retrofit
    private var api: WordApi? = null

    val tmdbApi: WordApi
        get() {
            if (api == null) {
                api = mRetrofit.create(WordApi::class.java)
            }
            return api!!
        }

    init {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    companion object {
        private val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
        private var mInstance: NetworkService? = null

        val instance: NetworkService
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance!!
            }
    }
}