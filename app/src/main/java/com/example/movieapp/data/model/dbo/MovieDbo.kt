package com.example.movieapp.data.model.dbo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.movieapp.data.model.dto.toGenreDvo
import com.example.movieapp.data.model.dto.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo

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
    var liked: Boolean = true
)

fun MovieDbo.toMovieDvo() = MovieDvo(
        movieId = this.movieId,
        budget = this.budget,
        homepage = this.homepage,
        runtime = this.runtime,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        liked = this.liked
).apply {
    genres = this@toMovieDvo.genres?.map { genreDto ->  genreDto.toGenreDvo()}
}