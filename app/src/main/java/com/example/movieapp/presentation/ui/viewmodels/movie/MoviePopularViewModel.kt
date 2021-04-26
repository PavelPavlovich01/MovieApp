package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.repository.MovieDtoRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val popularMovieViewModelModule = module {
    viewModel { MoviePopularViewModel(get()) }
}

class MoviePopularViewModel(private val movieDtoRepository: MovieDtoRepository) : ViewModel() {
    val popularMovies: LiveData<MovieResponseDto> = liveData {
        emit(movieDtoRepository.getPopularMovies(1))
    }
}