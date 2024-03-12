package kz.onelab.fifth_lesson

import android.app.Application
import kz.onelab.fifth_lesson.data.RetrofitHelper
import kz.onelab.fifth_lesson.data.repository.PopularMoviesRepository

class MoviesApplication : Application() {

    val moviesRepository: PopularMoviesRepository by lazy(LazyThreadSafetyMode.NONE) {
        PopularMoviesRepository(RetrofitHelper.getMoviesApi)
    }
}