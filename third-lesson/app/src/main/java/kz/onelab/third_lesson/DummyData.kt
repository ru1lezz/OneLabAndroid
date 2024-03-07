package kz.onelab.third_lesson

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyData (
    var text: String = "",
    var booleanTypeForExample: Boolean = false,
    var intTypeForExample: Int = 0
) : Parcelable