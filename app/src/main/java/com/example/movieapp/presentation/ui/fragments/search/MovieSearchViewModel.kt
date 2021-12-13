package com.example.movieapp.presentation.ui.fragments.search

import androidx.lifecycle.*
import com.example.movieapp.data.model.dto.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.repository.remote.MovieDtoRepository
import com.example.movieapp.presentation.ui.fragments.containers.TabSearchContainerFragment
import com.example.movieapp.util.Constants
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.system.exitProcess

val searchMovieViewModelModule = module {
    viewModel { (router: Router) -> MovieSearchViewModel(get(), router)  }
}

class MovieSearchViewModel(private val movieDtoRepository: MovieDtoRepository,
                           private val router: Router) : ViewModel() {

    private val searchResults = MutableLiveData<List<MovieDvo>?>()
    val moviesLiveData: LiveData<List<MovieDvo>?>
        get() = searchResults

    fun searchMovies(query: String) {
        viewModelScope.launch {
            searchResults.setValue(movieDtoRepository.searchMovieAsync(query).movies?.map {
                it.toMovieDvo()
            })
        }
    }

    fun onItemClicked(movieId: Int){
        TabSearchContainerFragment.getInstance(Constants.SEARCH_TAB).replaceScreenToDetails(movieId)
    }

    fun onBackPressed() {
        router.exit()
        exitProcess(0)
    }
}