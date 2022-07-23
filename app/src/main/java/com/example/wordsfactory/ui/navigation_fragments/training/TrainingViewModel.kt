package com.example.wordsfactory.ui.navigation_fragments.training

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wordsfactory.R

class TrainingViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application

    fun getText(size: Int): CharSequence {
        return app.getString(R.string.thereAre) + " $size " + app.getString(R.string.wordsInDictionary)
    }

}
