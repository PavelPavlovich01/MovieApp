package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.*
import com.example.movieapp.data.model.dbo.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.network.DataBaseException
import com.example.movieapp.data.repository.local.MovieDboRepository
import com.example.movieapp.presentation.ui.fragments.containers.TabFavouriteContainerFragment
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.system.exitProcess

val favouriteMovieViewModelModule = module {
    viewModel { (router: Router) -> MovieFavouriteViewModel(get(), router) }
}

class MovieFavouriteViewModel(private val movieDboRepository: MovieDboRepository,
                              private val router: Router) : ViewModel() {

    lateinit var favouriteMovies: State<LiveData<List<MovieDvo>?>>

    fun getFavouriteMovies() {
        favouriteMovies = State.loading()
        try{
            viewModelScope.launch {
                favouriteMovies = State.success(movieDboRepository.getAllMovies().asLiveData().map { it?.map { it.toMovieDvo() } })
            }
        } catch (e: DataBaseException) {
            favouriteMovies = State.error(e.message)
        }
    }

    fun onItemClicked(movieId: Int) {
        TabFavouriteContainerFragment.getInstance(Constants.FAVOURITE_TAB).replaceScreenToDetails(movieId)
    }

    fun onBackPressed() {
        router.exit()
        exitProcess(0)
    }
}