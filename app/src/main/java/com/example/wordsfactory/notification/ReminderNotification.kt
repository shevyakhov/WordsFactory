package com.example.wordsfactory.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.example.wordsfactory.R

class ReminderNotification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo) // TODO: check the SDK differences
                .setColor(Color.rgb(227, 86, 42))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(
                    intent.getStringExtra(
                        titleExtra
                    )
                )
                .setContentText(intent.getStringExtra(messageExtra))
                .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId, notification)

    }

    companion object Constants {
        const val notificationId = 1
        const val channelId = "channel1"
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"
    }
}