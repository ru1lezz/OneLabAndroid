package kz.onelab.eighth_lesson.core

import android.util.Log
import kz.onelab.eighth_lesson.core.functional.Result

open class BaseRepository {

    protected suspend fun <T : Any> apiCall(call: suspend () -> T): Result<Throwable, T> =
        try {
            Result.Success(call.invoke())
        } catch (throwable: Throwable) {
            Log.e("apiCall", "error", throwable)
            Result.Error(throwable)
        }
}