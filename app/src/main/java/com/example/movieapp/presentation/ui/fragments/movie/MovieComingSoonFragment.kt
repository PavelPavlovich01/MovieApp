package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.movieapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.model.mapper.toMovieDbo
import com.example.movieapp.databinding.FragmentMovieComingSoonBinding
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.movieapp.presentation.ui.adapter.MovieAdapter.OnItemClickListener
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.fragments.containers.TabComingSoonContainerFragment
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieComingSoonViewModel
import com.example.movieapp.util.Constants
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentComingSoonModule = module {
    factory { MovieComingSoonFragment() }
}

class MovieComingSoonFragment : Fragment(), OnItemClickListener, BackButtonListener {
    private lateinit var adapter: MovieAdapter

    private val movieComingSoonViewModel by viewModel<MovieComingSoonViewModel>{
            parametersOf((parentFragment as RouterProvider).router)
    }

    private val viewBinding by viewBinding<FragmentMovieComingSoonBinding>()

    private val observer = Observer<MovieResponseDto> {
        with(viewBinding){
            upcomingRecyclerview.setHasFixedSize(false)
            adapter = MovieAdapter(context as FragmentActivity, it.movies!!)
            adapter.setOnItemClickListener(this@MovieComingSoonFragment)
            upcomingRecyclerview.adapter = adapter
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieComingSoonViewModel.upcomingMovies.observe(viewLifecycleOwner, observer)
        return inflater.inflate(R.layout.fragment_movie_coming_soon, container, false)
    }

    //TODO("implement?")
    override fun onItemClicked(view: View, movieId: Int) {
        TabComingSoonContainerFragment.getInstance(Constants.UPCOMING_TAB).replaceScreenToDetails(movieId)
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
