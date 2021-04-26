package com.example.movieapp.data.model.mapper

import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dto.MovieDto

fun MovieDto.toMovieDbo() = MovieDbo(
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
    genres = this@toMovieDbo.genres?.map { genreDto ->  genreDto.toGenreDbo(movieId!!)}
}