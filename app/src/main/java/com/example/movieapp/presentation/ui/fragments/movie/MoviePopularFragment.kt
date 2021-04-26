package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.R
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.model.mapper.toMovieDbo
import com.example.movieapp.databinding.FragmentMoviePopularBinding
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.movieapp.presentation.ui.viewmodels.movie.MoviePopularViewModel
import com.example.movieapp.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val fragmentPopularModule = module {
    factory { MoviePopularFragment() }
}

class MoviePopularFragment : Fragment() {
    private val moviePopularViewModel by viewModel<MoviePopularViewModel>()

    private val viewBinding by viewBinding<FragmentMoviePopularBinding>()

    private val observer = Observer<MovieResponseDto> {
        with(viewBinding){
            popularRecyclerview.setHasFixedSize(false)
            popularRecyclerview.adapter = MovieAdapter(context as FragmentActivity, it.movies!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        moviePopularViewModel.popularMovies.observe(viewLifecycleOwner, observer)
        return inflater.inflate(R.layout.fragment_movie_popular, container, false)
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

