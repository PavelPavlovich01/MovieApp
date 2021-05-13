package com.example.movieapp.data.model.dvo

import com.example.movieapp.data.model.dbo.GenreDbo

data class GenreDvo(
    var id: Int? = null,
    var movieId: Int? = null,
    var name: String? = null
)

fun GenreDvo.toGenreDbo() = GenreDbo(
    id = this.id,
    movieId = this.movieId,
    name = this.name
)
