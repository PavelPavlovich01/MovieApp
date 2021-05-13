package com.example.movieapp.data.model.dto

import com.squareup.moshi.Json

data class TrailerResponseDto(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "results") //TODO("Remove comment")
    val trailers: List<TrailerDto>//?
)