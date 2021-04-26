package com.example.movieapp.data.model.dto

import com.squareup.moshi.Json

data class TrailerDto(
        @field:Json(name = "key")
        val key: String
)