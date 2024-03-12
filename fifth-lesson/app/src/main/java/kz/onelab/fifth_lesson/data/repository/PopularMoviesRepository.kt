package kz.onelab.fifth_lesson.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.onelab.fifth_lesson.core.BaseRepository
import kz.onelab.fifth_lesson.core.functional.State
import kz.onelab.fifth_lesson.data.api.MoviesApi
import kz.onelab.fifth_lesson.data.mapper.toPopularMovies
import kz.onelab.fifth_lesson.presentation.model.PopularMovies

class PopularMoviesRepository(
    private val moviesApi: MoviesApi
) : BaseRepository() {

    suspend fun getPopularMovies(): State<Throwable, PopularMovies> = apiCall {
        withContext(Dispatchers.IO) {
            moviesApi.getPopularMovies().toPopularMovies()
        }
    }
}
