package kz.onelab.eighth_lesson.core.functional

import android.util.Log
import kz.onelab.eighth_lesson.core.functional.Result.Error
import kz.onelab.eighth_lesson.core.functional.Result.Success

sealed class Result<out E, out D> {
    data class Error<out E>(val a: E) : Result<E, Nothing>()

    data class Success<out D>(val b: D) : Result<Nothing, D>()
}

inline fun <E, D> Result<E, D>.onSuccess(action: (D) -> Unit): Result<E, D> {
    if (this is Success) action(b)
    return this
}

inline fun <E, D> Result<E, D>.onFailure(action: (E) -> Unit): Result<E, D> {
    if (this is Error) {
        if (a is Throwable) {
            Log.e("Either#onFailure","Either#onFailure called", a)
        }
        action(a)
    }
    return this
}