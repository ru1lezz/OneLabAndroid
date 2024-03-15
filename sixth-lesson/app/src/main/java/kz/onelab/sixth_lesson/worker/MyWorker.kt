package kz.onelab.sixth_lesson.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kz.onelab.sixth_lesson.domain.FileUploader

class MyWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    private val fileUploader = FileUploader()

    override suspend fun doWork(): Result {
        fileUploader.upload(object : FileUploader.ProgressCallback {
            override fun onProgress(progress: Int) {
                if (isStopped) {
                    fileUploader.cancel()
                } else {
                    println("CoroutineWorker progress $progress%")
                }
            }
        })

        return Result.success()
    }
}