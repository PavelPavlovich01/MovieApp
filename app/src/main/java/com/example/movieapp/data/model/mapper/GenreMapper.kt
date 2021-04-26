package com.example.movieapp.data.model.mapper

import com.example.movieapp.data.model.dbo.GenreDbo
import com.example.movieapp.data.model.dto.GenreDto

fun GenreDto.toGenreDbo(movieId: Int) = GenreDbo(
        id = this.id,
        movieId = movieId,
        name = this.name
)