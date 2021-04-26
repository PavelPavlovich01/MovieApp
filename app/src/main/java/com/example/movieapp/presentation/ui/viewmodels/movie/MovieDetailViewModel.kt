package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dto.MovieDto
import com.example.movieapp.data.model.dto.TrailerResponseDto
import com.example.movieapp.data.model.mapper.toMovieDbo
import com.example.movieapp.data.repository.MovieDboRepository
import com.example.movieapp.data.repository.MovieDtoRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailMovieViewModelModule = module {
    viewModel { (movieId: Int, router: Router) -> MovieDetailViewModel(get(), get(), movieId, router) }
}

class MovieDetailViewModel(private val movieDtoRepository: MovieDtoRepository,
                           private val movieDboRepository: MovieDboRepository,
                           movieId: Int, private val router: Router) : ViewModel() {

    val detailMovie: LiveData<*> = liveData {
        if(!movieDboRepository.isExist(movieId))
            emit(movieDtoRepository.getMovieById(movieId))
        else {
            val movieDbo = movieDtoRepository.getMovieById(movieId).toMovieDbo()
            movieDboRepository.update(movieDbo)
            emit(movieDbo)
        }
    }

    val trailerMovieDto: LiveData<TrailerResponseDto> = liveData {
        emit(movieDtoRepository.getMovieTrailer(movieId))
    }

    //TODO("Diff types db or dt)
    fun onLikePressed(movie: Any) = viewModelScope.launch{
        if(movie is MovieDbo){
            if(movieDboRepository.isExist(movie.movieId!!)){
                movieDboRepository.delete(movie)
            } else {
                movieDboRepository.insert(movie)
            }
        } else if(movie is MovieDto){
            if(movieDboRepository.isExist(movie.movieId!!)){
                movieDboRepository.delete(movie.toMovieDbo())
            }
            else {
                movieDboRepository.insert(movie.toMovieDbo())
            }
        }
    }

    fun onBackPressed() {
        router.exit()
    }
}