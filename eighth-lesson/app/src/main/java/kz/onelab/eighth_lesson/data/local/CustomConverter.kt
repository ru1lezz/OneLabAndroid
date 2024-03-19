package kz.onelab.eighth_lesson.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kz.onelab.eighth_lesson.presentation.model.Movie
import kz.onelab.eighth_lesson.data.model.SomeStatus
import java.util.Date

class CustomConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMovieList(movies: List<Movie>?): String {
        if (movies == null) return ""
        return json.encodeToString(ListSerializer(Movie.serializer()), movies)
    }

    @TypeConverter
    fun toMovieList(movieString: String?): List<Movie> {
        if (movieString.isNullOrEmpty()) return emptyList()
        return json.decodeFromString(ListSerializer(Movie.serializer()), movieString)
    }

    @TypeConverter
    fun someEnumToString(someStatus: SomeStatus) = someStatus.name

    @TypeConverter
    fun stringToSomeEnum(someStatusName: String) = SomeStatus.valueOf(someStatusName)

    @TypeConverter
    fun dateToLong(date: Date?): Long? = date?.time

    @TypeConverter
    fun longToDate(time: Long?): Date? = time?.let { Date(it) }
}