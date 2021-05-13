package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.*
import com.example.movieapp.data.model.dbo.toMovieDvo
import com.example.movieapp.data.model.dto.TrailerResponseDto
import com.example.movieapp.data.model.dto.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.model.dvo.toMovieDbo
import com.example.movieapp.data.model.mapper.toMovieDbo
import com.example.movieapp.data.network.ApiException
import com.example.movieapp.data.network.NoInternetExсeption
import com.example.movieapp.data.repository.MovieDboRepository
import com.example.movieapp.data.repository.MovieDtoRepository
import com.example.movieapp.util.State
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailMovieViewModelModule = module {
    viewModel { (movieId: Int, router: Router) -> MovieDetailViewModel(get(), get(), movieId, router) }
}

class MovieDetailViewModel(private val movieDtoRepository: MovieDtoRepository,
                           private val movieDboRepository: MovieDboRepository,
                           private val movieId: Int, private val router: Router) : ViewModel() {

    private val _detailMovie = MutableLiveData<State<MovieDvo>>()
    val detailMovie: LiveData<State<MovieDvo>>
        get() = _detailMovie

    private val _trailerMovie = MutableLiveData<State<TrailerResponseDto>>()
    val trailerMovie: LiveData<State<TrailerResponseDto>>
        get() = _trailerMovie

    fun getMovieDetails(){
        _detailMovie.postValue(State.loading())
        _trailerMovie.postValue(State.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(!movieDboRepository.isExist(movieId)){
                    _detailMovie.postValue(State.success(movieDtoRepository.getMovieById(movieId).toMovieDvo()))
                } else {
                    val movieDbo = movieDtoRepository.getMovieById(movieId).toMovieDbo()
                    movieDboRepository.update(movieDbo)
                    _detailMovie.postValue(State.success(movieDbo.toMovieDvo()))
                }
            } catch (e: ApiException){
                _detailMovie.postValue(State.error(e.message))
            } catch (e: NoInternetExсeption){
                _detailMovie.postValue(State.error(e.message))
            }

            try {
                _trailerMovie.postValue(State.success(movieDtoRepository.getMovieTrailer(movieId)))
            } catch (e: ApiException){
                _trailerMovie.postValue(State.error(e.message))
            } catch (e: NoInternetExсeption){
                _trailerMovie.postValue(State.error(e.message))
            }
        }
    }

    fun onLikePressed(movie: MovieDvo) = viewModelScope.launch{
        if(movieDboRepository.isExist(movie.movieId!!)){
            movieDboRepository.delete(movie.toMovieDbo())
        } else {
            movieDboRepository.insert(movie.toMovieDbo())
        }
    }

    fun onBackPressed() {
        router.exit()
    }
}