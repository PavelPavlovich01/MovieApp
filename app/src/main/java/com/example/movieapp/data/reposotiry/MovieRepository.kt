package com.example.movieapp.data.reposotiry

import com.example.movieapp.data.network.MovieApi
import org.koin.dsl.module

val movieModule = module {
    factory { MovieRepository(get()) }
}

class MovieRepository(private val movieApi: MovieApi) {
    suspend fun getMovieById(movieId : Int) = movieApi.getMovieByIdAsync(movieId)
    suspend fun getPopularMovies(page : Int) = movieApi.getPopularMoviesAsync(page)
    suspend fun getUpcomingMovies(page : Int) = movieApi.getUpcomingMoviesAsync(page)
}