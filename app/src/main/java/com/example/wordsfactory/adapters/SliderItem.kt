package com.example.wordsfactory.adapters

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.wordsfactory.R

data class SliderItem(
    @DrawableRes val image: Int,
    @StringRes val mainText: Int,
    @StringRes val subText: Int
)

val introList = listOf(
    SliderItem(R.drawable.kid_long, R.string.introFirstMain, R.string.introSub),
    SliderItem(R.drawable.kids_home, R.string.introSecondMain, R.string.introSub),
    SliderItem(R.drawable.kids_tech, R.string.introThirdMain, R.string.introSub)
)