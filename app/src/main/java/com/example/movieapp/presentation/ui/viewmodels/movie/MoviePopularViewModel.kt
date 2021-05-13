package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.data.repository.MovieDtoRepository
import com.example.movieapp.domain.PostDataSource
import com.example.movieapp.presentation.ui.activity.MovieActivity
import com.example.movieapp.presentation.ui.fragments.containers.TabComingSoonContainerFragment
import com.example.movieapp.presentation.ui.fragments.containers.TabPopularContainerFragment
import com.example.movieapp.util.Constants
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.system.exitProcess

val popularMovieViewModelModule = module {
    viewModel { (router: Router) -> MoviePopularViewModel(get(), router)  }
}

class MoviePopularViewModel(private val movieApi: MovieDtoRepository,
                            private val router: Router) : ViewModel() {

    val popularMovies = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(movieApi)
    }.flow.cachedIn(viewModelScope)

    fun onItemClicked(movieId: Int){
        TabPopularContainerFragment.getInstance(Constants.POPULAR_TAB).replaceScreenToDetails(movieId)
    }

    fun onBackPressed(){
        router.exit()
        exitProcess(0)
    }
}