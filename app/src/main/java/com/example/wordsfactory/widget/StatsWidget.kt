package com.example.wordsfactory.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.wordsfactory.R

/**
 * Implementation of App Widget functionality.
 */
class StatsWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val sharedPrefsKey = "SHARED_PREFS"
    val learnedKey = "SHARED_PREFS_LEARNED"
    val allKey = "SHARED_PREFS_ALL"

    val sharedPrefs = context.getSharedPreferences(sharedPrefsKey, 0)
    val learned = sharedPrefs.getInt(learnedKey, 0)
    val all = sharedPrefs.getInt(allKey, 0)
    val textConstructor = { value: Int -> "$value Words" }
    val views = RemoteViews(context.packageName, R.layout.stats_widget)
    views.setTextViewText(R.id.appwidget_text_my_dictionary_stats, textConstructor.invoke(all))
    views.setTextViewText(R.id.appwidget_text_remember_stats, textConstructor.invoke(learned))
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
