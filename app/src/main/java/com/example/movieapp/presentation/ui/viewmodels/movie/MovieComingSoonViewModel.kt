package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.network.ApiException
import com.example.movieapp.data.repository.remote.MovieDtoRepository
import com.example.movieapp.presentation.ui.adapter.PostDataSource
import com.example.movieapp.presentation.ui.fragments.containers.TabComingSoonContainerFragment
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.system.exitProcess

val upcomingMovieViewModelModule = module {
    viewModel { (router: Router) -> MovieComingSoonViewModel(get(), router) }
}

class MovieComingSoonViewModel(private val movieApi: MovieDtoRepository,
                               private val router: Router) : ViewModel() {

    val upcomingMovies = try {
        State.Success(Pager(PagingConfig(pageSize = 20)) {
            PostDataSource(movieApi, Constants.UPCOMING_TAB)
        }.flow.cachedIn(viewModelScope))
    } catch (e: ApiException) {
        State.Error(e.message)
    }

    fun onItemClicked(movieId: Int){
        TabComingSoonContainerFragment.getInstance(Constants.UPCOMING_TAB).replaceScreenToDetails(movieId)
    }

    fun onBackPressed() {
        router.exit()
        exitProcess(0)
    }
}