package kz.onelab.fifth_lesson.core.functional

sealed class Resource<out Data> {
    data object Loading : Resource<Nothing>()
    data object Empty : Resource<Nothing>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    data class Success<out Data>(val data: Data) : Resource<Data>()
}