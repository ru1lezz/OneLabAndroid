package kz.onelab.eighth_lesson.domain

import kotlinx.coroutines.flow.Flow
import kz.onelab.eighth_lesson.core.functional.State
import kz.onelab.eighth_lesson.presentation.model.Movie

interface PopularMoviesRepository {

    val observeMoviesStateFlow: Flow<State<List<Movie>>>

    suspend fun fetchPopularMovies()
}