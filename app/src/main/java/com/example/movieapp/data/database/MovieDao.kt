package com.example.movieapp.data.database

import androidx.room.*
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.relationshipsutils.MovieWithGenres

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieDbo>?

    @Transaction
    @Query("SELECT * FROM movie")
    suspend fun getAllMoviesWithGenres(): List<MovieWithGenres>?

    @Query("SELECT * FROM movie WHERE movieId = :mid")
    suspend fun getMovieById(mid: Int): MovieDbo?

    @Transaction
    @Query("SELECT * FROM movie WHERE movieId = :mid")
    suspend fun getMovieWithGenresById(mid: Int): MovieDbo?

    @Query("SELECT EXISTS (SELECT 1 FROM movie WHERE movieId = :mid)")
    suspend fun isExist(mid: Int): Boolean

    @Update
    suspend fun update(movie: MovieDbo)

    @Insert
    suspend fun insert(movie: MovieDbo)

    @Delete()
    suspend fun delete(movie: MovieDbo)
}