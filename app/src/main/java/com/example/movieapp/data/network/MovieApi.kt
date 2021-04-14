package com.example.movieapp.data.network

import com.example.movieapp.data.model.MovieDetail
import com.example.movieapp.data.model.MovieResultsModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/movie/{movie_id}")
    suspend fun getMovieByIdAsync(@Query("movie_id") movieId: Int) : Deferred<Response<MovieDetail>>

    @GET("/movie/popular")
    suspend fun getPopularMoviesAsync(@Query("page") page: Int) : Deferred<Response<MovieResultsModel>>

    @GET("/movie/upcoming")
    suspend fun getUpcomingMoviesAsync(@Query("page") page: Int) : Deferred<Response<MovieResultsModel>>
}