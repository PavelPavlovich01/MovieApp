package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.databinding.FragmentMovieComingSoonBinding
import com.example.movieapp.presentation.ui.adapter.FooterStateAdapter
import com.example.movieapp.presentation.ui.adapter.MoviePagingAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieComingSoonViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentComingSoonModule = module {
    factory { MovieComingSoonFragment() }
}

class MovieComingSoonFragment : Fragment(), MoviePagingAdapter.OnItemClickListener, BackButtonListener {
    private lateinit var myAdapter: MoviePagingAdapter

    private val viewBinding by viewBinding<FragmentMovieComingSoonBinding>()

    private val movieUpcomingViewModel by viewModel<MovieComingSoonViewModel> {
        parametersOf((parentFragment as RouterProvider).router)
    }

    private fun setupList() {
        myAdapter = MoviePagingAdapter()
        with(viewBinding){
            upcomingRecyclerview.apply {
                myAdapter.setOnItemClickListener(this@MovieComingSoonFragment)
                myAdapter.addLoadStateListener { state ->
                    upcomingRecyclerview.isVisible = state.refresh != LoadState.Loading
                    progressBar.isVisible = state.refresh == LoadState.Loading
                }
                myAdapter.withLoadStateFooter(FooterStateAdapter())
                layoutManager = LinearLayoutManager(context)
                adapter = myAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()
        viewLifecycleOwner.lifecycleScope.launch {
            when(movieUpcomingViewModel.upcomingMovies){
                is State.Success -> {
                    (movieUpcomingViewModel.upcomingMovies as State.Success<Flow<PagingData<MovieDvo>>>).data.collect{
                        myAdapter.submitData(it)
                    }
                }
                is State.Error -> {
                    Toast.makeText(context, (movieUpcomingViewModel.upcomingMovies as State.Error).message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_movie_coming_soon, container, false)
    }

    override fun onItemClicked(movieId: Int) {
        movieUpcomingViewModel.onItemClicked(movieId)
    }

    override fun onBackPressed(): Boolean {
        movieUpcomingViewModel.onBackPressed()
        return true
    }

    companion object{
        fun getInstance(name: String): MovieComingSoonFragment {
            return MovieComingSoonFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}
