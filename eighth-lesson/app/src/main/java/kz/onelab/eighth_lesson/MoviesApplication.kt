package kz.onelab.eighth_lesson

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}