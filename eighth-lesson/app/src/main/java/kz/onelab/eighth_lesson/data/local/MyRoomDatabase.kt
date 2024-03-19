package kz.onelab.eighth_lesson.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.onelab.eighth_lesson.data.model.MovieEntity

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
}