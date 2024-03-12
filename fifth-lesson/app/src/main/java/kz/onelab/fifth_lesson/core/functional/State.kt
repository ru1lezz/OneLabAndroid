package kz.onelab.fifth_lesson.core.functional

import android.util.Log

sealed class State<out E, out D> {
    data class Error<out E>(val a: E) : State<E, Nothing>()

    data class Success<out D>(val b: D) : State<Nothing, D>()
}

inline fun <E, D> State<E, D>.onSuccess(action: (D) -> Unit): State<E, D> {
    if (this is State.Success) action(b)
    return this
}

inline fun <E, D> State<E, D>.onFailure(action: (E) -> Unit): State<E, D> {
    if (this is State.Error) {
        if (a is Throwable) {
            Log.e("Either#onFailure","Either#onFailure called", a)
        }
        action(a)
    }
    return this
}