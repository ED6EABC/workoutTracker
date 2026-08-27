package com.eelizarraras.workout.core.domine.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.graphics.drawable.Icon
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.utils.NotificationConstants
import androidx.core.net.toUri

class CustomNotificationsManager(
    private val context: Context
) {

    val soundUri: Uri =
        "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/raw/notification".toUri()

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

    internal fun playSound() {
        try {
            val ringtone = RingtoneManager.getRingtone(context, soundUri)
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun buildNotification(
        context: Context,
        title: String,
        content: String,
        pendingIntent: PendingIntent,
        isPaused: Boolean,
        isResting: Boolean,
        doneExercises: Int,
        totalExercises: Int,
        pauseIntent: PendingIntent? = null,
        resumeIntent: PendingIntent? = null,
        skipRestIntent: PendingIntent? = null
    ): android.app.Notification {

        val builder = NotificationCompat.Builder(context, NotificationConstants.WORKOUT_NOTIFICATION_CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine(content)
                    .addLine("$doneExercises/$totalExercises ejercicios")
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setContentIntent(pendingIntent)

        if (!isResting) {
            builder.setLargeIcon(Icon.createWithResource(context, R.drawable.ic_timelapse))

            if (isPaused) {
                resumeIntent?.let {
                    builder.addAction(R.drawable.ic_play_arrow, "Play", it)
                }
            } else {
                pauseIntent?.let {
                    builder.addAction(R.drawable.ic_pause, "Pause", it)
                }
            }
        } else {
            builder.setLargeIcon(Icon.createWithResource(context, R.drawable.ic_rest))
            skipRestIntent?.let {
                builder.addAction(R.drawable.ic_play_arrow, "Skip", it)
            }
        }

        return builder.build()
    }
}