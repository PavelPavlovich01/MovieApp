package com.example.movieapp.data.model.dvo

data class MovieResponseDvo(
        var page: Int? = null,
        var movies: List<MovieDvo>? = null,
        var totalPages: Int? = null
)