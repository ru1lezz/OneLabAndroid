package kz.onelab.eighth_lesson

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.eighth_lesson.core.notification.MovieNotification
import kz.onelab.eighth_lesson.core.notification.MovieNotificationManager
import javax.inject.Inject

@AndroidEntryPoint
class CustomBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var movieNotificationManager: MovieNotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "TEST_ACTION") {
            movieNotificationManager.showNotification(
                MovieNotification(
                    title = "тайтл",
                    text = "текст",
                    channelId = "update cache",
                    channelName = R.string.movie_update_cache_channel_name,
                    icon = R.mipmap.ic_launcher,
                    channelDescription = R.string.movie_cache_update
                )
            )
        }
    }
}