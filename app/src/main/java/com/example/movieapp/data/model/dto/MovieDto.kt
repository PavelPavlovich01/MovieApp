package com.example.movieapp.data.model.dto

import com.example.movieapp.data.model.dvo.MovieDvo
import com.squareup.moshi.Json

data class MovieDto(
    @field:Json(name = "id")
    val movieId: Int? = null,
    @field:Json(name = "budget")
    val budget: Int? = null,
    @field:Json(name = "homepage")
    val homepage: String? = null,
    @field:Json(name = "runtime")
    val runtime: Int? = null,
    @field:Json(name = "original_title")
    val title: String? = null,
    @field:Json(name = "poster_path")
    val posterPath: String? = null,
    @field:Json(name = "overview")
    val overview: String? = null,
    @field:Json(name = "release_date")
    val releaseDate: String? = null,
    @field:Json(name = "vote_average")
    val voteAverage: Double? = null,
    @field:Json(name = "genres")
    val genres: List<GenreDto>? = null
)

fun MovieDto.toMovieDvo() = MovieDvo(
        movieId = this.movieId,
        budget = this.budget,
        homepage = this.homepage,
        runtime = this.runtime,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
).apply {
    genres = this@toMovieDvo.genres?.map { genreDto ->  genreDto.toGenreDvo(movieId!!)}
}

