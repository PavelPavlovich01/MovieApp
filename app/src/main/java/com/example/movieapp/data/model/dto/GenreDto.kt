package com.example.movieapp.data.model.dto

import com.example.movieapp.data.model.dvo.GenreDvo
import com.squareup.moshi.Json

data class GenreDto(
        @field:Json(name = "id")
        val id: Int? = null,
        @field:Json(name = "name")
        val name: String? = null
)

fun GenreDto.toGenreDvo(movieId: Int) = GenreDvo(
    id = this.id,
    movieId = movieId,
    name = this.name
)