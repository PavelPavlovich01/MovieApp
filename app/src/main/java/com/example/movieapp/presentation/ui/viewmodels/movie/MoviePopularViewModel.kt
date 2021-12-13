package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.network.ApiException
import com.example.movieapp.data.repository.remote.MovieDtoRepository
import com.example.movieapp.presentation.ui.adapter.PostDataSource
import com.example.movieapp.presentation.ui.fragments.containers.TabPopularContainerFragment
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.system.exitProcess

val popularMovieViewModelModule = module {
    viewModel { (router: Router) -> MoviePopularViewModel(get(), router)  }
}

class MoviePopularViewModel(private val movieApi: MovieDtoRepository,
                            private val router: Router) : ViewModel() {

    val popularMovies = try {
        State.Success(Pager(PagingConfig(pageSize = 20)) {
            PostDataSource(movieApi, Constants.POPULAR_TAB)
        }.flow.cachedIn(viewModelScope))
    } catch (e: ApiException) {
        State.Error(e.message)
    }

    fun onItemClicked(movieId: Int){
        TabPopularContainerFragment.getInstance(Constants.POPULAR_TAB).replaceScreenToDetails(movieId)
    }

    fun onBackPressed(){
        router.exit()
        exitProcess(0)
    }
}