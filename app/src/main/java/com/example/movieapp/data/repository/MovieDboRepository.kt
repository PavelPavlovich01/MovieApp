package com.example.movieapp.data.repository

import com.example.movieapp.data.database.MovieDao
import com.example.movieapp.data.model.dbo.MovieDbo
import org.koin.dsl.module

val movieDboBModule = module {
    factory { MovieDboRepository(get()) }
}

class MovieDboRepository(private val movieDao: MovieDao) {
    suspend fun getAllMovies(): List<MovieDbo>? {return movieDao.getAllMovies()}
    suspend fun getAllMoviesWithGenres() {movieDao.getAllMoviesWithGenres()}
    suspend fun getMovieById(mid: Int){movieDao.getMovieById(mid)}
    suspend fun getMovieWithGenresById(mid: Int){movieDao.getMovieWithGenresById(mid)}
    suspend fun isExist(mid: Int): Boolean = movieDao.isExist(mid)
    suspend fun update(movie: MovieDbo){movieDao.update(movie)}
    suspend fun insert(movie: MovieDbo){movieDao.insert(movie)}
    suspend fun delete(movie: MovieDbo){movieDao.delete(movie)}
}