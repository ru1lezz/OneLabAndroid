package kz.onelab.sixth_lesson.domain

import kotlinx.coroutines.delay

class FileUploader {

    interface ProgressCallback {
        fun onProgress(progress: Int)
    }

    private var isWorking = false

    suspend fun upload(progressCallback: ProgressCallback) {
        isWorking = true
        repeat(100) {
            if (!isWorking) return@repeat

            delay(100L)
            progressCallback.onProgress(it)
        }
    }

    fun cancel() {
        isWorking = false
    }
}