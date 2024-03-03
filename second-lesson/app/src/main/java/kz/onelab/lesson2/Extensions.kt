package kz.onelab.lesson2

import android.content.res.Resources
import android.util.TypedValue

val Number.sp: Int
    get() {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), metrics).toInt()
    }