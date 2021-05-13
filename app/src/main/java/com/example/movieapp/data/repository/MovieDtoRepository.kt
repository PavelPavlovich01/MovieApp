package com.example.movieapp.data.repository

import com.example.movieapp.data.model.dto.MovieDto
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.model.dto.TrailerResponseDto
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.data.network.SafeApiRequest
import org.koin.dsl.module
import retrofit2.http.Query

val movieDtoModule = module {
    factory { MovieDtoRepository(get()) }
}

class MovieDtoRepository(private val movieApi: MovieApi) : SafeApiRequest() {
    suspend fun getMovieById(movieId : Int) : MovieDto { return apiRequest {movieApi.getMovieByIdAsync(movieId)} }
    suspend fun getMovieTrailer(movieId : Int) : TrailerResponseDto { return apiRequest {movieApi.getMovieTrailerAsync(movieId)} }
    suspend fun getPopularMovies(page : Int)  : MovieResponseDto { return apiRequest { movieApi.getPopularMoviesAsync(page) } }
    suspend fun getUpcomingMovies(page : Int) : MovieResponseDto { return apiRequest { movieApi.getUpcomingMoviesAsync(page) } }
    suspend fun searchMovieAsync(query: String) : MovieResponseDto { return apiRequest { movieApi.searchMovieAsync(query) } }
}