package com.example.movieapp.data.model.dbo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieDbo(
    @PrimaryKey(autoGenerate = false)
    var movieId: Int? = null,
    var budget: Int? = null,
    var homepage: String? = null,
    var runtime: Int? = null,
    var title: String? = null,
    var posterPath: String? = null,
    var overview: String? = null,
    var releaseDate: String? = null,
    var voteAverage: Double? = null,
    @Ignore
    var genres: List<GenreDbo>? = null,
    var liked: Boolean? = true
)