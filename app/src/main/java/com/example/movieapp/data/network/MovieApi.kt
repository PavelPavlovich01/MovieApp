package com.example.movieapp.data.network

import com.example.movieapp.data.model.dto.MovieDto
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.model.dto.TrailerResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieByIdAsync(@Path("movie_id") movieId: Int) : Response<MovieDto>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailerAsync(@Path("movie_id") movieId: Int) : Response<TrailerResponseDto>

    @GET("movie/popular")
    suspend fun getPopularMoviesAsync(@Query("page") page: Int) : Response<MovieResponseDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesAsync(@Query("page") page: Int) : Response<MovieResponseDto>
}