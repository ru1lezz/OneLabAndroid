package kz.onelab.eighth_lesson.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String
)