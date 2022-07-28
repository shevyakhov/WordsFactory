package com.example.wordsfactory.dictionary_logic.repository

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.dictionary_logic.database.AppDatabase
import com.example.wordsfactory.dictionary_logic.database.WordDao
import com.example.wordsfactory.dictionary_logic.repository.app_viewmodel.AppViewModelFactory
import com.example.wordsfactory.dictionary_logic.repository.app_viewmodel.ResultViewModelFactory
import com.example.wordsfactory.dictionary_logic.repository.retrofit.NetworkService

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
        return AppViewModelFactory(provideRepository(context))
    }

    fun provideFactoryResult(context: Context): ViewModelProvider.Factory {
        return ResultViewModelFactory(provideRepository(context))
    }

}