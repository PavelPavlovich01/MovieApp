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
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieFavouriteViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentFavouriteModule = module {
    factory { MovieFavouriteFragment() }
}

class MovieFavouriteFragment : Fragment(), MovieAdapter.OnItemClickListener, BackButtonListener {
    private var myAdapter: MovieAdapter  = MovieAdapter()

    private val movieFavouriteViewModel by viewModel<MovieFavouriteViewModel> {
        parametersOf((parentFragment as RouterProvider).router)
    }

    private val viewBinding by viewBinding<FragmentMovieFavouriteBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_favourite, container, false)
    }

    override fun onResume() {
        super.onResume()
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
                    (movieFavouriteViewModel.favouriteMovies as State.Success<LiveData<List<MovieDvo>?>>).data.observe(viewLifecycleOwner, object : Observer<List<MovieDvo>?>{
                        override fun onChanged(t: List<MovieDvo>?) {
                            if(t != null){
                                if(t.isEmpty()){
                                    noMoviesTv.visibility = View.VISIBLE
                                }
                                myAdapter.setData(t)
                                favouriteRecyclerview.adapter = myAdapter
                                favouriteRecyclerview.visibility = View.VISIBLE
                                noMoviesTv.visibility = View.GONE
                            } else {
                                favouriteRecyclerview.visibility = View.GONE
                                noMoviesTv.visibility = View.VISIBLE
                            }
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
