package com.eelizarraras.workout.core.domine.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.utils.NotificationConstants

class CustomNotificationsManager(
    private val context: Context
) {

    internal fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationConstants.WORKOUT_NOTIFICATION_CHANNEL_ID,
                NotificationConstants.WORKOUT_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(context, NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    internal fun buildNotification(
        context: Context,
        title: String,
        content: String,
        pendingIntent: PendingIntent
    ): android.app.Notification {

        return NotificationCompat.Builder(context, NotificationConstants.WORKOUT_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .build()
    }

}