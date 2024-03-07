package kz.onelab.third_lesson

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

sealed class ListItem(val id: UUID = UUID.randomUUID()) : Parcelable {

    @Parcelize
    data class Header(
        val title: String = ""
    ): ListItem()

    @Parcelize
    data class Content(
        val title: String = "",
        val subtitle: String = "",
        val imageUrl: String = ""
    ): ListItem()
}