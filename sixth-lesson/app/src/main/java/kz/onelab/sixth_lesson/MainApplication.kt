package kz.onelab.sixth_lesson

import android.app.Application
import kz.onelab.sixth_lesson.repository.MyRepository
import kz.onelab.sixth_lesson.repository.local.MyRoomDatabase

class MainApplication : Application() {

    val myRepository: MyRepository by lazy {
        MyRepository(roomDatabase.movieDao())
    }
    private lateinit var roomDatabase: MyRoomDatabase

    override fun onCreate() {
        super.onCreate()
        roomDatabase = MyRoomDatabase.create(applicationContext)
    }
}