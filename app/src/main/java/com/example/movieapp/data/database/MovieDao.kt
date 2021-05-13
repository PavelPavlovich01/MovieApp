package com.example.movieapp.data.database

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.relationshipsutils.MovieWithGenres
import com.example.movieapp.util.State
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieDbo>?>

    @Query("SELECT EXISTS (SELECT 1 FROM movie WHERE movieId = :mid)")
    suspend fun isExist(mid: Int): Boolean

    @Update
    suspend fun update(movie: MovieDbo)

    @Insert
    suspend fun insert(movie: MovieDbo)

    @Delete
    suspend fun delete(movie: MovieDbo)
}