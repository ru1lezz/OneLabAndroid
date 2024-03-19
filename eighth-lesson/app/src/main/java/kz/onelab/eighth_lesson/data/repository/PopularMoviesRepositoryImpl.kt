package kz.onelab.eighth_lesson.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import kz.onelab.eighth_lesson.core.NetworkChecker
import kz.onelab.eighth_lesson.core.functional.State
import kz.onelab.eighth_lesson.data.api.MoviesApi
import kz.onelab.eighth_lesson.data.local.MovieDao
import kz.onelab.eighth_lesson.data.mapper.toEntity
import kz.onelab.eighth_lesson.data.mapper.toMovie
import kz.onelab.eighth_lesson.data.mapper.toPresentation
import kz.onelab.eighth_lesson.domain.PopularMoviesRepository
import kz.onelab.eighth_lesson.presentation.model.Movie
import javax.inject.Inject

class PopularMoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao,
    private val networkChecker: NetworkChecker
) : PopularMoviesRepository {

    private val _moviesDataFlow = MutableStateFlow<State<List<Movie>>>(State.Initial)
    override val observeMoviesStateFlow: Flow<State<List<Movie>>>
        get() = _moviesDataFlow

    override suspend fun fetchPopularMovies() =
        withContext(Dispatchers.IO) {
            try {
                val cachedMovies = movieDao.getAll()
                if (cachedMovies.isEmpty()) {
                    _moviesDataFlow.emit(State.Loading)
                } else {
                    Log.d("PopMovie Repo", "Cache Film")
                    _moviesDataFlow.emit(State.Data(cachedMovies.map { it.toPresentation() }))
                }
                if (networkChecker.isConnected) {
                    Log.d("PopMovie Repo", "Internet is connected")
                    val movies = moviesApi.getPopularMovies().results
                    _moviesDataFlow.emit(State.Data(movies.map { it.toMovie() }))
                    movieDao.clearAndInsert(movies.map { it.toEntity() })
                } else if (cachedMovies.isEmpty()) {
                    _moviesDataFlow.emit(State.Failure(Exception("No Internet and no cache")))
                }
            } catch (throwable: Throwable) {
                _moviesDataFlow.emit(State.Failure(throwable))
            }
        }
}
