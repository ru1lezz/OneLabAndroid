package kz.onelab.eighth_lesson.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kz.onelab.eighth_lesson.BuildConfig
import kz.onelab.eighth_lesson.core.interceptor.QueryInterceptor
import kz.onelab.eighth_lesson.data.api.MoviesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitHelper {

    private val client = OkHttpClient.Builder()
        .addInterceptor(QueryInterceptor(hashMapOf("api_key" to BuildConfig.MOVIE_DB_API_KEY)))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    val getMoviesApi : MoviesApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create(MoviesApi::class.java)
    }
}