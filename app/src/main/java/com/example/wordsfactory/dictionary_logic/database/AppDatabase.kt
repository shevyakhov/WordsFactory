package com.example.wordsfactory.dictionary_logic.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [WordEntity::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val LOG_TAG: String = AppDatabase::class.simpleName.toString()
        private val LOCK: Any = Object()
        private const val DATABASE_NAME: String = "words"

        @Volatile
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, AppDatabase.DATABASE_NAME
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance!!
        }

    }


    abstract fun dao(): WordDao

}