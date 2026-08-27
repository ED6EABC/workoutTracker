package com.eelizarraras.workout.core.domine

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import com.eelizarraras.workout.MainActivity
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.domine.notifications.CustomNotificationsManager
import com.eelizarraras.workout.core.domine.utils.formatSeconds
import com.eelizarraras.workout.core.utils.NotificationConstants
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.utils.toRestTimeString
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.RestTimerUseCase
import com.eelizarraras.workout.flows.routine.playRoutine.domine.use_case.TimerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class TimerService : Service(), KoinComponent {

    private val timerUseCase: TimerUseCase by inject()
    private val restTimerUseCase: RestTimerUseCase by inject()

    private val notificationManager: CustomNotificationsManager by inject()

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager.createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startForegroundService()
            ACTION_STOP -> stopService()
            ACTION_PAUSE -> timerUseCase.pause()
            ACTION_RESUME -> timerUseCase.resume()
        }
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        timerUseCase.stop()
        restTimerUseCase.stop()
        stopService()
    }

    private fun startForegroundService() {
        val content = getString(R.string.notification_timer_content, "00:00:00")
        val notification = buildNotification(
            title = getString(R.string.notification_workout_title),
            content = content,
            isPaused = timerUseCase.isPaused.value,
            isResting = restTimerUseCase.isRunning.value,
            doneExercises = timerUseCase.doneExercises.value,
            totalExercises = timerUseCase.totalExercises.value
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NotificationConstants.WORKOUT_NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(NotificationConstants.WORKOUT_NOTIFICATION_ID, notification)
        }
        observeTimers()
    }

    private fun buildNotification(
        title: String,
        content: String,
        isPaused: Boolean,
        isResting: Boolean,
        doneExercises: Int,
        totalExercises: Int
    ): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val pauseIntent = PendingIntent.getService(
            this, 1,
            Intent(this, TimerService::class.java).apply { action = ACTION_PAUSE },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val resumeIntent = PendingIntent.getService(
            this, 2,
            Intent(this, TimerService::class.java).apply { action = ACTION_RESUME },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return notificationManager.buildNotification(
            context = this,
            title = title,
            content = content,
            pendingIntent = pendingIntent,
            isPaused = isPaused,
            isResting = isResting,
            doneExercises = doneExercises,
            totalExercises = totalExercises,
            pauseIntent = pauseIntent,
            resumeIntent = resumeIntent
        )
    }

    private fun observeTimers() {
        serviceScope.launch {
            combine<Any, Unit>(
                timerUseCase.elapsedSeconds,
                restTimerUseCase.remainingSeconds,
                restTimerUseCase.isRunning,
                timerUseCase.isPaused,
                timerUseCase.doneExercises,
                timerUseCase.totalExercises
            ) { args ->
                val elapsed = args[0] as Long
                val remaining = args[1] as Int
                val isResting = args[2] as Boolean
                val isPaused = args[3] as Boolean
                val done = args[4] as Int
                val total = args[5] as Int

                val title = if (isResting) {
                    getString(R.string.notification_rest_title)
                } else {
                    getString(R.string.notification_workout_title)
                }

                val content = if (isResting) {
                    getString(R.string.notification_rest_content, remaining.toRestTimeString())
                } else {
                    getString(R.string.notification_timer_content, formatSeconds(elapsed))
                }

                val notification = buildNotification(
                    title = title,
                    content = content,
                    isPaused = isPaused,
                    isResting = isResting,
                    doneExercises = done,
                    totalExercises = total
                )

                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.notify(
                    NotificationConstants.WORKOUT_NOTIFICATION_ID,
                    notification
                )
            }.collect {}
        }
    }

    private fun stopService() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_RESUME = "ACTION_RESUME"
    }
}
