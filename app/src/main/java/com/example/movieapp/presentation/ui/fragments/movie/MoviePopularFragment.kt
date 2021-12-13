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
import com.example.movieapp.databinding.FragmentMoviePopularBinding
import com.example.movieapp.presentation.ui.adapter.FooterStateAdapter
import com.example.movieapp.presentation.ui.adapter.MoviePagingAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MoviePopularViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import java.lang.Exception

val fragmentPopularModule = module {
    factory { MoviePopularFragment() }
}

class MoviePopularFragment : Fragment(), MoviePagingAdapter.OnItemClickListener, BackButtonListener {
    private lateinit var myAdapter: MoviePagingAdapter

    private val viewBinding by viewBinding<FragmentMoviePopularBinding>()

    private val moviePopularViewModel by viewModel<MoviePopularViewModel> {
        parametersOf((parentFragment as RouterProvider).router)
    }

    private fun setupList() {
        myAdapter = MoviePagingAdapter()
        with(viewBinding){
            popularRecyclerview.apply {
                myAdapter.setOnItemClickListener(this@MoviePopularFragment)
                myAdapter.withLoadStateFooter(footer = FooterStateAdapter())
                myAdapter.addLoadStateListener { state ->
                    popularRecyclerview.isVisible = state.refresh != LoadState.Loading
                    progressBar.isVisible = state.refresh == LoadState.Loading
                }
                layoutManager = LinearLayoutManager(context)
                adapter = myAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()
        viewLifecycleOwner.lifecycleScope.launch {
            when(moviePopularViewModel.popularMovies){
                is State.Success -> {
                    (moviePopularViewModel.popularMovies as State.Success<Flow<PagingData<MovieDvo>>>).data.collect{
                        myAdapter.submitData(it)
                    }
                }
                is State.Error -> {
                    Toast.makeText(context, (moviePopularViewModel.popularMovies as State.Error).message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    throw Exception("Un")
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_movie_popular, container, false)
    }

    override fun onItemClicked(movieId: Int) {
        moviePopularViewModel.onItemClicked(movieId)
    }

    override fun onBackPressed(): Boolean {
        moviePopularViewModel.onBackPressed()
        return true
    }

    companion object{
        fun getInstance(name: String): MoviePopularFragment {
            return MoviePopularFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}

