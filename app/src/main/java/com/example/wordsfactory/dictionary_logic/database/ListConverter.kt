package com.example.wordsfactory.dictionary_logic.database

import androidx.room.TypeConverter
import com.example.wordsfactory.adapters.WordItem

class ListConverter {
    /* Turn List of DefinitionEntities into combined string where elements divided by "|" */
    @TypeConverter
    fun fromListToOne(value: List<WordItem>): String {
        var string = ""
        for (i in value.indices) {
            string += value[i].definition + "|" + value[i].example
            if (i != value.size - 1)
                string += "^"
        }
        return string
    }

    @TypeConverter
    fun fromOneToList(value: String): List<WordItem> {
        val result = mutableListOf<WordItem>()
        val step = value.split("^")

        for (i in step) {
            val group = i.split("|")
            val item = WordItem(group[0], group[1])
            result.add(item)
        }

        return result
    }
}