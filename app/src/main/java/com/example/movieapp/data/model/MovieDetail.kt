package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class MovieDetail(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "original_title")
    val title: String,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "overview")
    val overview: String,
    @field:Json(name = "release_date")
    val releaseDate: String,
    @field:Json(name = "vote_average")
    val voteAverage: Int,
    @field:Json(name = "video")
    val video: Boolean,
    @field:Json(name = "genres")
    val genres: ArrayList<Genre?>
){
    data class Genre(
        @field:Json(name = "id")
        val id: Int,
        @field:Json(name = "name")
        val name: Int
    )
}