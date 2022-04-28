package com.example.wordsfactory.dictionary_logic.database

import androidx.room.TypeConverter
import com.example.wordsfactory.ui.navigation_fragments.dictionary.adapter.WordItem


class ListConverter {
    private val emptyString = ""
    private val wordItemDivider = "^"
    private val classValueDivider = "|"

    /* Turn List of WordItem into combined string where elements divided by "^" and elements of class divided by "|"  */
    @TypeConverter
    fun fromListToOne(wordItemList: List<WordItem>): String {
        var messyString = emptyString
        for (i in wordItemList.indices) {
            messyString += wordItemList[i].definition + classValueDivider + wordItemList[i].example
            if (i != wordItemList.size - 1)
                messyString += wordItemDivider
        }
        return messyString
    }

    /* reversed function  */
    @TypeConverter
    fun fromOneToList(value: String): List<WordItem> {
        val wordItemList = mutableListOf<WordItem>()
        val items = value.split(wordItemDivider)

        for (i in items) {
            val group = i.split(classValueDivider)
            val item = WordItem(group[0], group[1])
            wordItemList.add(item)
        }

        return wordItemList
    }
}