package kz.onelab.sixth_lesson.repository.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kz.onelab.sixth_lesson.repository.COLUMN_NAME_MOVIE_DESCRIPTION
import kz.onelab.sixth_lesson.repository.COLUMN_NAME_MOVIE_ID
import kz.onelab.sixth_lesson.repository.COLUMN_NAME_MOVIE_NAME
import kz.onelab.sixth_lesson.repository.COLUMN_NAME_MOVIE_POSTER_URL
import kz.onelab.sixth_lesson.repository.COLUMN_NAME_MOVIE_RELEASE_DATE
import kz.onelab.sixth_lesson.repository.TABLE_NAME_MOVIE

@Entity(tableName = TABLE_NAME_MOVIE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_MOVIE_ID)
    val movieId: Long,

    @ColumnInfo(name = COLUMN_NAME_MOVIE_NAME)
    val movieName: String,

    @ColumnInfo(name = COLUMN_NAME_MOVIE_DESCRIPTION)
    val movieDescription: String,

    @ColumnInfo(name = COLUMN_NAME_MOVIE_POSTER_URL)
    val moviePosterUrl: String,

    @ColumnInfo(name = COLUMN_NAME_MOVIE_RELEASE_DATE)
    val movieReleaseDate: String,

//    @Embedded
//    val someClass: SomeClass
)

data class SomeClass(
    val str: String,
    val num: Int
)

enum class SomeStatus {
    SET, NOT_SET, REFUSED
}