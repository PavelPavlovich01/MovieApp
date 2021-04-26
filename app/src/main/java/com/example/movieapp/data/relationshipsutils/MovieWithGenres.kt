package com.example.movieapp.data.relationshipsutils

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movieapp.data.model.dbo.GenreDbo
import com.example.movieapp.data.model.dbo.MovieDbo

data class MovieWithGenres (
    @Embedded val movieDbo: MovieDbo,
    @Relation(
            parentColumn = "movieId",
            entityColumn = "movieId"
    )
    val genres: List<GenreDbo>
)
