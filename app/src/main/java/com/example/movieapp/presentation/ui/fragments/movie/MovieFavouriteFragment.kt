package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.databinding.FragmentMovieFavouriteBinding
import com.example.movieapp.presentation.ui.adapter.MovieListAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieFavouriteViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentFavouriteModule = module {
    factory { MovieFavouriteFragment() }
}

class MovieFavouriteFragment : Fragment(), MovieListAdapter.OnItemClickListener, BackButtonListener {
    private var myAdapter: MovieListAdapter  = MovieListAdapter()

    private val movieFavouriteViewModel by viewModel<MovieFavouriteViewModel> {
        parametersOf((parentFragment as RouterProvider).router)
    }

    private val viewBinding by viewBinding<FragmentMovieFavouriteBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieFavouriteViewModel.getFavouriteMovies()
        with(viewBinding){
            when(movieFavouriteViewModel.favouriteMovies){
                is State.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    favouriteRecyclerview.visibility = View.GONE
                    noMoviesTv.visibility = View.GONE
                }
                is State.Success -> {
                    progressBar.visibility = View.GONE
                    (movieFavouriteViewModel.favouriteMovies as State.Success<LiveData<List<MovieDvo>?>>).data.observe(viewLifecycleOwner, { t ->
                        if(t != null){
                            if(t.isEmpty()){
                                myAdapter.submitList(emptyList())
                                noMoviesTv.visibility = View.VISIBLE
                            } else {
                                myAdapter.submitList(t)
                                favouriteRecyclerview.adapter = myAdapter
                                favouriteRecyclerview.visibility = View.VISIBLE
                                noMoviesTv.visibility = View.GONE
                            }
                        } else {
                            favouriteRecyclerview.visibility = View.GONE
                            noMoviesTv.visibility = View.VISIBLE
                        }
                    })
                    favouriteRecyclerview.setHasFixedSize(false)
                    myAdapter.setOnItemClickListener(this@MovieFavouriteFragment)
                }
                is State.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Exception with DB", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClicked(view: View, movieId: Int) {
        movieFavouriteViewModel.onItemClicked(movieId)
    }

    override fun onBackPressed(): Boolean {
        movieFavouriteViewModel.onBackPressed()
        return true
    }

    companion object{
        fun getInstance(name: String): MovieFavouriteFragment {
            return MovieFavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}