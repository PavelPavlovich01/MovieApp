package com.example.movieapp.presentation.ui.viewmodels.movie

import androidx.lifecycle.*
import com.example.movieapp.data.model.dto.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.model.dvo.MovieResponseDvo
import com.example.movieapp.data.network.ApiException
import com.example.movieapp.data.network.NoInternetExсeption
import com.example.movieapp.data.repository.MovieDtoRepository
import com.example.movieapp.presentation.ui.Screens
import com.example.movieapp.presentation.ui.fragments.containers.TabComingSoonContainerFragment
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.system.exitProcess

val upcomingMovieViewModelModule = module {
    viewModel { (router: Router) -> MovieComingSoonViewModel(get(), router) }
}

class MovieComingSoonViewModel(private val movieRepository: MovieDtoRepository,
                               private val router: Router) : ViewModel() {

    private var page: Int? = null
    private var maxPages: Int? = null

    private val _loadMoreList = MutableLiveData<Boolean>()
    val loadMoreList: LiveData<Boolean>
        get() = _loadMoreList

    private val _upcomingMovies = MutableLiveData<State<List<MovieDvo>?>>()
    val upcomingMovies: LiveData<State<List<MovieDvo>?>>
        get() = _upcomingMovies

    init {
        page = 1
        _loadMoreList.value = false
        getUpcomingMovies()
        initCountPages()
    }

    fun getUpcomingMovies() {
        _upcomingMovies.postValue(State.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _upcomingMovies.postValue(State.success(movieRepository.getUpcomingMovies(page!!).movies?.map {
                    it.toMovieDvo()
                }))
            } catch (e: ApiException){
                _upcomingMovies.postValue(State.error(e.message))
            } catch (e: NoInternetExсeption){
                _upcomingMovies.postValue(State.error(e.message))
            }
        }
    }

    private fun initCountPages(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                maxPages = movieRepository.getUpcomingMovies(1).totalPages
            } catch (e: ApiException){
                _upcomingMovies.postValue(State.error(e.message))
            } catch (e: NoInternetExсeption){
                _upcomingMovies.postValue(State.error(e.message))
            }
        }
    }

    fun loadMore() {
        if(page!! < maxPages!!){
            page = page?.plus(1)
            getUpcomingMovies()
            _loadMoreList.value = false
        }
    }

    fun checkForLoadMoreItems(visibleItemCount: Int, totalItemCount: Int, firstVisibleItemPosition: Int) {
        if (!_loadMoreList.value!!) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                _loadMoreList.postValue(true)
            }
        }
    }

    fun onItemClicked(movieId: Int){
        TabComingSoonContainerFragment.getInstance(Constants.UPCOMING_TAB).replaceScreenToDetails(movieId)
    }

    fun onBackPressed() {
        router.exit()
        exitProcess(0)
    }
}