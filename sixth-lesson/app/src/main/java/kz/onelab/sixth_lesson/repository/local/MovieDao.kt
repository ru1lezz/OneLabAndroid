package kz.onelab.sixth_lesson.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kz.onelab.sixth_lesson.repository.TABLE_NAME_MOVIE
import kz.onelab.sixth_lesson.repository.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM $TABLE_NAME_MOVIE")
    suspend fun getAll(): List<MovieEntity>

    @Insert
    suspend fun insert(entity: MovieEntity)

    @Query("DELETE FROM $TABLE_NAME_MOVIE")
    suspend fun deleteAll()
}