package kz.onelab.sixth_lesson.repository

import kz.onelab.sixth_lesson.presentation.model.Movie
import kz.onelab.sixth_lesson.repository.local.MovieDao
import kz.onelab.sixth_lesson.repository.mappers.toEntity
import kz.onelab.sixth_lesson.repository.mappers.toPresentation

class MyRepository(
    private val movieDao: MovieDao
)  {

    suspend fun getAllMovies() : Result<List<Movie>> {
        return Result.success(movieDao.getAll().map { it.toPresentation() })
    }

    suspend fun insertMovie(movie: Movie) = movieDao.insert(movie.toEntity())
}