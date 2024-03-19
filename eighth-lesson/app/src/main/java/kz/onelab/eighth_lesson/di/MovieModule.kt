package kz.onelab.eighth_lesson.di

import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kz.onelab.eighth_lesson.BuildConfig
import kz.onelab.eighth_lesson.core.NetworkChecker
import kz.onelab.eighth_lesson.core.interceptor.QueryInterceptor
import kz.onelab.eighth_lesson.core.notification.MovieNotificationManager
import kz.onelab.eighth_lesson.data.api.MoviesApi
import kz.onelab.eighth_lesson.domain.PopularMoviesRepository
import kz.onelab.eighth_lesson.data.repository.PopularMoviesRepositoryImpl
import kz.onelab.eighth_lesson.data.DATABASE_NAME_MOVIES
import kz.onelab.eighth_lesson.data.local.MovieDao
import kz.onelab.eighth_lesson.data.local.MyRoomDatabase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(QueryInterceptor(hashMapOf("api_key" to BuildConfig.MOVIE_DB_API_KEY)))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(okHttpClient: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRoomDatabase(@ApplicationContext context: Context) : MyRoomDatabase {
        return Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java,
            DATABASE_NAME_MOVIES
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(myRoomDatabase: MyRoomDatabase) : MovieDao {
        return myRoomDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(
        movieDao: MovieDao,
        moviesApi: MoviesApi,
        networkChecker: NetworkChecker
    ): PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(
            moviesApi = moviesApi,
            movieDao = movieDao,
            networkChecker = networkChecker
        )
    }

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideMovieNotificationManager(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): MovieNotificationManager {
        return MovieNotificationManager(context, notificationManager)
    }
}