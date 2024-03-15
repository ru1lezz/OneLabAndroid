package kz.onelab.sixth_lesson.repository.mappers

import kz.onelab.sixth_lesson.presentation.model.Movie
import kz.onelab.sixth_lesson.repository.model.MovieEntity

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