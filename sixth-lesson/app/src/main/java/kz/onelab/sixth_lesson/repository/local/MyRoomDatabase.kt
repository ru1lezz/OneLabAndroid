package kz.onelab.sixth_lesson.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.onelab.sixth_lesson.repository.DATABASE_NAME_MOVIES
import kz.onelab.sixth_lesson.repository.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1
)
@TypeConverters(
    CustomConverter::class
)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        fun create(applicationContext: Context): MyRoomDatabase = Room.databaseBuilder(
            applicationContext,
            MyRoomDatabase::class.java,
            DATABASE_NAME_MOVIES
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}