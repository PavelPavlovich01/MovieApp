package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.repository.MovieDtoRepository
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val upcomingMovieViewModelModule = module {
    viewModel { /*(page: Int,*/ (router: Router) -> MovieComingSoonViewModel(get(), /*page,*/ router) }
}

class MovieComingSoonViewModel(private val movieRepository: MovieDtoRepository,
                               //val page: Int,
                               private val router: Router) : ViewModel() {
    val upcomingMovies: LiveData<MovieResponseDto> = liveData {
        emit(movieRepository.getUpcomingMovies(1))
    }

    fun onBackPressed() {
        router.exit()
    }
}