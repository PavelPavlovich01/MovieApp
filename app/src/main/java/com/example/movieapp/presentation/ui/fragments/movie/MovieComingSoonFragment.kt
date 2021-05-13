package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.databinding.FragmentMovieComingSoonBinding
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieComingSoonViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentComingSoonModule = module {
    factory { MovieComingSoonFragment() }
}

class MovieComingSoonFragment : Fragment(), MovieAdapter.OnItemClickListener, BackButtonListener {
    private lateinit var myAdapter: MovieAdapter

    private val movieComingSoonViewModel by viewModel<MovieComingSoonViewModel>{
            parametersOf((parentFragment as RouterProvider).router)
    }

    private val viewBinding by viewBinding<FragmentMovieComingSoonBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_movie_coming_soon, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupUI()
        initializeObserver()
        setupAPICall()
    }

    private fun setupUI(){
        myAdapter = MovieAdapter()
        with(viewBinding){
            upcomingRecyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                itemAnimator = DefaultItemAnimator()
                myAdapter.setOnItemClickListener(this@MovieComingSoonFragment)
                adapter = myAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                        val visibleItemCount = layoutManager!!.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                        movieComingSoonViewModel.checkForLoadMoreItems(
                            visibleItemCount,
                            totalItemCount,
                            firstVisibleItemPosition
                        )
                    }
                })
            }
        }
    }

    private fun initializeObserver() {
        movieComingSoonViewModel.loadMoreList.observe(viewLifecycleOwner, Observer {
            if (it) {
                myAdapter.addData(null)
                Handler().postDelayed({
                    movieComingSoonViewModel.loadMore()
                }, 1000)
            }
        })
    }

    private fun setupAPICall() {
            movieComingSoonViewModel.upcomingMovies.observe(viewLifecycleOwner, { state ->
                with(viewBinding) {
                    when (state) {
                        is State.Loading -> {
                            upcomingRecyclerview.setVisibility(View.GONE)
                            progressBar.setVisibility(View.VISIBLE)
                        }
                        is State.Success -> {
                            upcomingRecyclerview.setVisibility(View.VISIBLE)
                            progressBar.setVisibility(View.GONE)
                            myAdapter.addData(state.data as ArrayList<MovieDvo>?)
                        }
                        is State.Error -> {
                            progressBar.setVisibility(View.GONE)
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    override fun onItemClicked(view: View, movieId: Int) {
        movieComingSoonViewModel.onItemClicked(movieId)
    }

    override fun onBackPressed(): Boolean {
        movieComingSoonViewModel.onBackPressed()
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
