package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.adapter.FooterStateAdapter
import com.example.movieapp.presentation.ui.adapter.MovieListAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MoviePopularViewModel
import com.example.movieapp.util.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentPopularModule = module {
    factory { MoviePopularFragment() }
}

class MoviePopularFragment : Fragment(), MovieListAdapter.OnItemClickListener, BackButtonListener {
    lateinit var myAdapter: MovieListAdapter
    lateinit var popularRecyclerview: RecyclerView

    private val moviePopularViewModel by viewModel<MoviePopularViewModel> {
        parametersOf((parentFragment as RouterProvider).router)
    }

    private fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            moviePopularViewModel.popularMovies.collect {
                myAdapter.withLoadStateFooter(footer = FooterStateAdapter())
                myAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        myAdapter = MovieListAdapter()
        popularRecyclerview.apply {
            myAdapter.setOnItemClickListener(this@MoviePopularFragment)
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(R.layout.fragment_movie_popular, container, false)
        popularRecyclerview = view.findViewById(R.id.popular_recyclerview)
        setupList()
        setupView()
        return view
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

