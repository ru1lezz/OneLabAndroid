package kz.onelab.fifth_lesson.data.mapper

import kz.onelab.fifth_lesson.data.model.MovieDto
import kz.onelab.fifth_lesson.data.model.PopularMoviesDto
import kz.onelab.fifth_lesson.presentation.model.Movie
import kz.onelab.fifth_lesson.presentation.model.PopularMovies

internal fun MovieDto.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterPath,
    releaseDate = releaseDate
)

internal fun PopularMoviesDto.toPopularMovies(): PopularMovies {
    return PopularMovies(
        popularMovieList = results.map { it.toMovie() }
    )
}
