package kz.onelab.sixth_lesson.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kz.onelab.sixth_lesson.presentation.MainActivity
import kz.onelab.sixth_lesson.domain.FileUploader
import kz.onelab.sixth_lesson.utils.NotificationFactory
import kz.onelab.sixth_lesson.utils.getPendingIntent

class MyForegroundService : Service() {

    private val fileUploader = FileUploader()
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("ForegroundService onStartCommand")

        val notificationFactory = NotificationFactory(
            context = this,
            pendingIntent = getPendingIntent(MainActivity::class.java),
        )

        startForeground(
            1,
            notificationFactory.create(title = "Uploading", text = "Starting...")
        )

        serviceScope.launch {
            fileUploader.upload(object : FileUploader.ProgressCallback {
                override fun onProgress(progress: Int) {
                    println("ForegroundService progress $progress%")

                    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(
                        1,
                        notificationFactory.create(text = "progress $progress%"),
                    )
                }
            })

            stopSelf()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}