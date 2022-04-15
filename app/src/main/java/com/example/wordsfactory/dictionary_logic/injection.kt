package com.example.wordsfactory.dictionary_logic

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.dictionary_logic.database.AppDatabase
import com.example.wordsfactory.dictionary_logic.database.WordDao

/* handmade di instead of using dagger*/
object Injection {
    private fun provideCache(context: Context): WordDao {
        val database = AppDatabase.getInstance(context)
        return database.dao()
    }

    private fun provideRepository(context: Context): AppRepository {
        return AppRepository(NetworkService.instance, provideCache(context))
    }

    fun provideFactory(context: Context): ViewModelProvider.Factory {
        return Factory(provideRepository(context))
    }

}