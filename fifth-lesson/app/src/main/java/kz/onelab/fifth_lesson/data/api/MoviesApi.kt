package kz.onelab.fifth_lesson.data.api

import kz.onelab.fifth_lesson.data.model.PopularMoviesDto
import retrofit2.http.GET

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(): PopularMoviesDto
}