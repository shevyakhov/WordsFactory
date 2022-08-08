package com.example.wordsfactory.dictionary_logic.repository.retrofit

import com.example.wordsfactory.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor() {
    private val mRetrofit: Retrofit
    private var mWordApi: WordApi? = null

    /*create wordApi instance*/
    val wordApi: WordApi
        get() {
            if (mWordApi == null) {
                mWordApi = mRetrofit.create(WordApi::class.java)
            }
            return mWordApi!!
        }

    init { /* init retrofit*/

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
        private const val BASE_URL = BuildConfig.BASE_URL
        private var mInstance: NetworkService? = null

        /*create NetworkService instance*/
        val instance: NetworkService
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance!!
            }
    }
}