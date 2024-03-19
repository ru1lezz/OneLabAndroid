package kz.onelab.eighth_lesson.core.functional

sealed class State<out Data> {
    class Failure(val exception: Throwable) : State<Nothing>()
    data object Loading : State<Nothing>()
    data object Initial : State<Nothing>()
    class Data<out Data>(val data: Data) : State<Data>()
}