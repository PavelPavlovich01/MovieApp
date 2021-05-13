package com.example.movieapp.data.model.dvo

import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dbo.toGenreDvo
import com.example.movieapp.data.model.dbo.toMovieDvo

data class MovieDvo(
        var movieId: Int? = null,
        var budget: Int? = null,
        var homepage: String? = null,
        var runtime: Int? = null,
        var title: String? = null,
        var posterPath: String? = null,
        var overview: String? = null,
        var releaseDate: String? = null,
        var voteAverage: Double? = null,
        var genres: List<GenreDvo>? = null,
        var liked: Boolean? = null
)

fun MovieDvo.toMovieDbo() = MovieDbo(
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
    genres = this@toMovieDbo.genres?.map { genreDvo ->  genreDvo.toGenreDbo()}
}
