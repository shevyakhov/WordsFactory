package com.example.wordsfactory.dictionary_logic.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = UserEntityColumnInfoName)
    var name: String,
    @ColumnInfo(name = UserEntityColumnInfoEmail)
    var email: String,
    @ColumnInfo(name = UserEntityColumnInfoPassword)
    var password: String
) {
    companion object {
        const val UserEntityColumnInfoName = "name"
        const val UserEntityColumnInfoEmail = "email"
        const val UserEntityColumnInfoPassword = "password"
    }
}