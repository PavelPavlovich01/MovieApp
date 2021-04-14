package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class MovieResultsModel (
    @field:Json(name = "page")
    val page: Int,
    @field:Json(name = "results")
    val movies: ArrayList<MovieDetail>,
    @field:Json(name = "total_pages")
    val totalPages: Int
)