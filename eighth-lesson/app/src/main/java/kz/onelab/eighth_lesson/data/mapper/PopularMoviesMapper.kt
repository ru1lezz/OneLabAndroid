package kz.onelab.eighth_lesson.data.mapper

import kz.onelab.eighth_lesson.data.model.PopularMoviesDto
import kz.onelab.eighth_lesson.presentation.model.PopularMovies

internal fun PopularMoviesDto.toPopularMovies(): PopularMovies {
    return PopularMovies(
        popularMovieList = results.map { it.toMovie() }
    )
}
