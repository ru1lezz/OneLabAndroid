package kz.onelab.eighth_lesson.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kz.onelab.eighth_lesson.data.TABLE_NAME_MOVIE
import kz.onelab.eighth_lesson.data.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM $TABLE_NAME_MOVIE")
    suspend fun getAll(): List<MovieEntity>

    @Insert
    suspend fun insert(entities: List<MovieEntity>)

    @Query("DELETE FROM $TABLE_NAME_MOVIE")
    suspend fun deleteAll()

    @Transaction
    suspend fun clearAndInsert(entities: List<MovieEntity>) {
        deleteAll()
        insert(entities)
    }
}