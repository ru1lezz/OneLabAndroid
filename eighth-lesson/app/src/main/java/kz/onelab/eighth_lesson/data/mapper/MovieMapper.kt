package kz.onelab.eighth_lesson.data.mapper

import kz.onelab.eighth_lesson.data.model.MovieDto
import kz.onelab.eighth_lesson.presentation.model.Movie
import kz.onelab.eighth_lesson.data.model.MovieEntity

internal fun MovieEntity.toPresentation() =
    Movie(
        id = movieId,
        title = movieName,
        overview = movieDescription,
        posterUrl = moviePosterUrl,
        releaseDate = movieReleaseDate
    )

internal fun Movie.toEntity() =
    MovieEntity(
        movieId = id,
        movieName = title,
        movieDescription = overview,
        moviePosterUrl = posterUrl,
        movieReleaseDate = releaseDate
    )

internal fun MovieDto.toEntity() =
    MovieEntity(
        movieId = id,
        movieName = title,
        movieDescription = overview,
        moviePosterUrl = posterPath,
        movieReleaseDate = releaseDate
    )

internal fun MovieDto.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterPath,
    releaseDate = releaseDate
)