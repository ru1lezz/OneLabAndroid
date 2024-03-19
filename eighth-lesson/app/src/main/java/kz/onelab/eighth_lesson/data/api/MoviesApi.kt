package kz.onelab.eighth_lesson.data.api

import kz.onelab.eighth_lesson.data.model.PopularMoviesDto
import retrofit2.http.GET

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(): PopularMoviesDto
}