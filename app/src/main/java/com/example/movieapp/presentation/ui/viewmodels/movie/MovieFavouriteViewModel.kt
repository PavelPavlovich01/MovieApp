package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.repository.MovieDboRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteMovieViewModelModule = module {
    viewModel { MovieFavouriteViewModel(get()) }
}

class MovieFavouriteViewModel(private val movieDboRepository: MovieDboRepository) : ViewModel() {
    val favouriteMovies: LiveData<List<MovieDbo>> = liveData {
        movieDboRepository.getAllMovies()?.let { emit(it) }
    }
}