package com.example.wordsfactory.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.example.wordsfactory.R
import com.example.wordsfactory.activity.MainActivity

class ReminderNotification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.rgb(227, 86, 42))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(
                    R.drawable.dictionary_ic,
                    context.getString(R.string.open),
                    openActivityIntent(context)
                )
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


    private fun openActivityIntent(context: Context): PendingIntent? {
        val resultIntent = Intent(context, MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        return resultPendingIntent
    }

    companion object Constants {
        const val notificationId = 1
        const val channelId = "channel1"
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"
    }
}