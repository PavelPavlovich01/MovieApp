package com.example.movieapp.data.repository

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.movieapp.data.database.MovieDao
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.util.State
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

val movieDboBModule = module {
    factory { MovieDboRepository(get()) }
}

class MovieDboRepository(private val movieDao: MovieDao) {
    fun getAllMovies(): Flow<List<MovieDbo>?> = movieDao.getAllMovies()
    suspend fun isExist(mid: Int): Boolean = movieDao.isExist(mid)
    suspend fun update(movie: MovieDbo){movieDao.update(movie)}
    suspend fun insert(movie: MovieDbo){movieDao.insert(movie)}
    suspend fun delete(movie: MovieDbo){movieDao.delete(movie)}
}