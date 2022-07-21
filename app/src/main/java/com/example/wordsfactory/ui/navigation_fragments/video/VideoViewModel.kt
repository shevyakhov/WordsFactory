package com.example.wordsfactory.ui.navigation_fragments.video

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wordsfactory.BuildConfig
import com.example.wordsfactory.R

class VideoViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    private var currentUrl: String = BuildConfig.VIDEO_URL


    fun currentUrl() = currentUrl
    fun saveHistory(new: String) {
        currentUrl = new
    }

    fun forbiddenLink(): String {
        return app.getString(R.string.forbiddenLink1) + "\n" + app.getString(R.string.forbiddenLink2)
    }
}
