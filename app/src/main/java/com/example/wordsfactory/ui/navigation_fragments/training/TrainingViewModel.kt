package com.example.wordsfactory.ui.navigation_fragments.training

import android.animation.ValueAnimator
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wordsfactory.R

class TrainingViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application

    fun getText(size: Int): CharSequence {
        return app.getString(R.string.thereAre) + " $size " + app.getString(R.string.wordsInDictionary)
    }

    fun pairList() = listOf(
        TrainingFragment.AnimationPair(app.getString(R.string.orange), app.getString(R.string._5)),
        TrainingFragment.AnimationPair(app.getString(R.string.blue), app.getString(R.string._4)),
        TrainingFragment.AnimationPair(app.getString(R.string.green), app.getString(R.string._3)),
        TrainingFragment.AnimationPair(app.getString(R.string.yellow), app.getString(R.string._2)),
        TrainingFragment.AnimationPair(app.getString(R.string.red), app.getString(R.string._1)),
        TrainingFragment.AnimationPair(app.getString(R.string.orange), app.getString(R.string._GO)),
    )

    fun colorValueAnimator(): ValueAnimator = ValueAnimator.ofArgb(
        app.getColor(R.color.primaryOrange),
        app.getColor(R.color.animBlue),
        app.getColor(R.color.animYellow),
        app.getColor(R.color.animGreen),
        app.getColor(R.color.animRed),
        app.getColor(R.color.primaryOrange)
    )

}
