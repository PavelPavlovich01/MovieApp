package com.example.movieapp.data.model.dto

import com.example.movieapp.data.model.dvo.MovieResponseDvo
import com.squareup.moshi.Json

data class MovieResponseDto (
    @field:Json(name = "page")
    val page: Int? = null,
    @field:Json(name = "results")
    val movies: List<MovieDto>? = null,
    @field:Json(name = "total_pages")
    val totalPages: Int? = null
)

fun MovieResponseDto.toMovieResponseDvo() = MovieResponseDvo(
    page = this.page,
    totalPages = this.totalPages
).apply {
    movies = this@toMovieResponseDvo.movies?.map { movieDto ->  movieDto.toMovieDvo()}
}

