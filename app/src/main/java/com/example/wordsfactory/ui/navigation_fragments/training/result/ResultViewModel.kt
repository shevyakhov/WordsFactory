package com.example.wordsfactory.ui.navigation_fragments.training.result

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.wordsfactory.R
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.repository.AppRepository
import com.example.wordsfactory.notification.NotificationHelper
import com.example.wordsfactory.widget.StatsWidget

class ResultViewModel(private val repository: AppRepository) : ViewModel() {


    fun updateDb(list: List<WordEntity>) {
        for (i in list) {
            repository.updateLearningRate(i)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotification(context: Context) {
        NotificationHelper.deleteReminders(context)
        NotificationHelper.setReminders(
            context = context,
            title = context.getString(R.string.reminderTitle),
            message = context.getString(R.string.reminderMsg),
            duration = NotificationHelper.tenSecondsInMilliseconds

        )
    }

    fun updateWidget(context: Context) {

        val statistics = getStatistics()
        val rememberWordsText = "${statistics.first} ${context.getString(R.string.widgetWordsText)}"
        val dictionaryWordsText =
            "${statistics.second} ${context.getString(R.string.widgetWordsText)}"

        val remoteViews = RemoteViews(context.packageName, R.layout.stats_widget)
        remoteViews.setTextViewText(R.id.appwidget_text_remember_stats, rememberWordsText)
        remoteViews.setTextViewText(R.id.appwidget_text_my_dictionary_stats, dictionaryWordsText)
        val appWidget = ComponentName(context, StatsWidget::class.java)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(appWidget, remoteViews)
    }

    fun getStatistics(): Pair<Int, Int> {

        val learningRates = repository.getEveryWord()?.map { it.learningRate }
        if (learningRates != null) {
            return learningRates.count { it == 1 } to learningRates.size
        }
        return 0 to 0
    }


}