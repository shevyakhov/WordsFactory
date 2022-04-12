package com.example.wordsfactory.dictionary_logic

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.wordsfactory.R
import com.example.wordsfactory.dictionary_logic.database.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordsApp : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var db: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        Log.e("init","!!!!!!!!!")
        db = Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "database"
            )
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

