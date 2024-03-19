package kz.onelab.eighth_lesson.core.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kz.onelab.eighth_lesson.presentation.MainActivity
import kz.onelab.eighth_lesson.utils.createNotification
import kz.onelab.eighth_lesson.utils.getPendingIntent
import java.util.UUID
import javax.inject.Inject

class MovieNotificationManager @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager
) {
    @SuppressLint("MissingPermission")
    fun showNotification(movieNotification: MovieNotification) {
        val pendingIntent = context.getPendingIntent(MainActivity::class.java)
        buildNotificationChannel(movieNotification)
        val notification = context.createNotification(
            movieNotification = movieNotification,
            pendingIntent = pendingIntent
        )
        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }

    private fun buildNotificationChannel(movieNotification: MovieNotification) {
        val chan = NotificationChannel(
            movieNotification.channelId,
            context.getString(movieNotification.channelName),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        if (notificationManager.notificationChannels.all { it.id != movieNotification.channelId }) {
            notificationManager.createNotificationChannel(chan)
        }
    }
}

data class MovieNotification(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val text: String,
    val channelId: String = UUID.randomUUID().toString(),
    @StringRes val channelName: Int,
    @DrawableRes val icon: Int,
    @StringRes val channelDescription: Int
)