package com.example.wordsfactory.ui.introduction_screens.intro.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.wordsfactory.R

/* item for viewPager adapter*/
data class SliderItem(
    @DrawableRes val image: Int,
    @StringRes val mainText: Int,
    @StringRes val subText: Int
)

/* list for viewPager adapter*/
val introList = listOf(
    SliderItem(R.drawable.kid_long, R.string.introFirstMain, R.string.introSub),
    SliderItem(R.drawable.kids_home, R.string.introSecondMain, R.string.introSub),
    SliderItem(R.drawable.kids_tech, R.string.introThirdMain, R.string.introSub)
)