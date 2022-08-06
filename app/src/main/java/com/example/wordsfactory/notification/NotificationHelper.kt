package com.example.wordsfactory.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.wordsfactory.R
import java.util.*

object NotificationHelper {

    const val dayInMilliseconds: Long = 24 * 60 * 60 * 1000


    @RequiresApi(Build.VERSION_CODES.O)
    fun setReminder(
        context: Context,
        title: String = context.getString(R.string.reminderTitle),
        message: String = context.getString(R.string.reminderMsg),
        duration: Long,
        multiplier: Int = 1
    ) {
        createNotificationChannel(context)

        val intent = Intent(context.applicationContext, ReminderNotification::class.java)
        intent.putExtra(ReminderNotification.titleExtra, title)
        intent.putExtra(ReminderNotification.messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            ReminderNotification.notificationId * multiplier, // varies from 1 to 2
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = Calendar.getInstance().time.time
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time + (duration * multiplier),
            pendingIntent
        )
        //sets the timer two times far from the first one
        // TODO: if visited the app in 2 days remake notification, othervise
        if (multiplier == 1) {
            setReminder(context, title, message, duration, multiplier = 2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.reminder)
        val desc = context.getString(R.string.reminderDescription)
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_DEFAULT
        } else {
            Log.e("failed", " fail")
        }
        val channel = NotificationChannel(ReminderNotification.channelId, name, importance)
        channel.description = desc

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteReminder(context: Context, title: String, message: String) {
        createNotificationChannel(context)
        val intent = Intent(context.applicationContext, ReminderNotification::class.java)
        intent.putExtra(ReminderNotification.titleExtra, title)
        intent.putExtra(ReminderNotification.messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            ReminderNotification.notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = Calendar.getInstance().time.time
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time + 10000,
            pendingIntent
        )
    }

}