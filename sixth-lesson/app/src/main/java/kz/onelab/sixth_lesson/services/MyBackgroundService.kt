package kz.onelab.sixth_lesson.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kz.onelab.sixth_lesson.domain.FileUploader

class MyBackgroundService : Service() {
    private val fileUploader = FileUploader()
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            fileUploader.upload(object : FileUploader.ProgressCallback {
                override fun onProgress(progress: Int) {
                    println("MyBackgroundService onProgress $progress%")
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