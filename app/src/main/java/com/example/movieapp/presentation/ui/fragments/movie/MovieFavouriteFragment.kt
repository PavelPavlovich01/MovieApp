package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.databinding.FragmentMovieFavouriteBinding
import com.example.movieapp.databinding.FragmentMoviePopularBinding
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieFavouriteViewModel
import com.example.movieapp.util.Constants
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val fragmentFavouriteModule = module {
    factory { MovieFavouriteFragment() }
}

class MovieFavouriteFragment : Fragment() {
    private val movieFavouriteViewModel by viewModel<MovieFavouriteViewModel>()

    private val viewBinding by inject<FragmentMovieFavouriteBinding>()

    private val observer = Observer<List<MovieDbo>> {
        with(viewBinding){
            favouriteRecyclerview.setHasFixedSize(false)
            //favouriteRecyclerview.adapter = MovieAdapter(context as FragmentActivity, it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieFavouriteViewModel.favouriteMovies.observe(viewLifecycleOwner, observer)
        return inflater.inflate(R.layout.fragment_movie_favourite, container, false)
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