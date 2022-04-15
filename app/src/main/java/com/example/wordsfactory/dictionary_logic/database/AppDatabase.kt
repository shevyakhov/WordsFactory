package com.example.wordsfactory.dictionary_logic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [WordEntity::class, UserEntity::class], version = 2)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    /* create database instance*/
    companion object {
        private val LOCK: Any = Object()
        private const val DATABASE_NAME: String = "words"

        @Volatile
        private var sInstance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    ).allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build()
                }
            }

            return sInstance!!
        }

    }

    /* get db Dao*/
    abstract fun dao(): WordDao

}