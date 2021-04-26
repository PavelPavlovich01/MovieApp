package com.example.movieapp.data.model.dto

import com.squareup.moshi.Json

data class GenreDto(
        @field:Json(name = "id")
        val id: Int? = null,
        @field:Json(name = "name")
        val name: String? = null
){
    override fun toString(): String {
        return "$name/"
    }
}