package com.example.movieapp.presentation.ui.fragments.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.dbo.MovieDbo
import com.example.movieapp.data.model.dto.MovieDto
import com.example.movieapp.data.model.dto.TrailerResponseDto
import com.example.movieapp.data.network.BuildConfig.BASE_POSTER_URL
import com.example.movieapp.databinding.MovieDetailFragmentBinding
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieDetailViewModel
import com.example.movieapp.util.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentDetailModule = module {
    factory { MovieDetailFragment() }
}

class MovieDetailFragment : Fragment(), BackButtonListener {

    private var flagSwap = false

    private val movieDetailViewModel: MovieDetailViewModel by viewModel{
        parametersOf(requireArguments().getInt(Constants.MOVIE_ID),
                (parentFragment as RouterProvider).router)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieDetailViewModel.detailMovie.observe(viewLifecycleOwner, observerDetails)
        movieDetailViewModel.trailerMovieDto.observe(viewLifecycleOwner, observerTrailer)
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    private val viewBinding by viewBinding<MovieDetailFragmentBinding>()

    //TODO("Dto and Dbo objects")
    @SuppressLint("SetTextI18n")
    private val observerDetails = Observer<Any> {
        with(viewBinding){
            if(it is MovieDto) {
                movieTitleTv.text = it.title
                movieDescriptionTv.text = it.overview
                movieDateTv.text = context?.resources?.getString(R.string.date) + it.releaseDate
                movieRatingTv.text = context?.resources?.getString(R.string.rating) + it.voteAverage.toString()
                var genres = ""
                for (name in it.genres!!) genres += name
                movieGenresTv.text = context?.resources?.getString(R.string.genres) + genres.subSequence(0, genres.length - 1)
                movieBudgetTv.text = context?.resources?.getString(R.string.budget) + it.budget.toString() + "$"
                movieRuntimeTv.text = context?.resources?.getString(R.string.runtime) + it.runtime.toString() + " min."
                Glide.with(this@MovieDetailFragment).load(BASE_POSTER_URL + it.posterPath).into(moviePosterIv)
                movieLikedIb.setBackgroundResource(R.drawable.notliked)
            } else if (it is MovieDbo){
                movieTitleTv.text = it.title
                movieDescriptionTv.text = it.overview
                movieDateTv.text = context?.resources?.getString(R.string.date) + it.releaseDate
                movieRatingTv.text = context?.resources?.getString(R.string.rating) + it.voteAverage.toString()
                var genres = ""
                for (name in it.genres!!) genres += name
                movieGenresTv.text = context?.resources?.getString(R.string.genres) + genres.subSequence(0, genres.length - 1)
                movieBudgetTv.text = context?.resources?.getString(R.string.budget) + it.budget.toString() + "$"
                movieRuntimeTv.text = context?.resources?.getString(R.string.runtime) + it.runtime.toString() + " min."
                Glide.with(this@MovieDetailFragment).load(BASE_POSTER_URL + it.posterPath).into(moviePosterIv)
                movieLikedIb.setBackgroundResource(R.drawable.liked)
                flagSwap = true
            }

            movieLikedIb.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    if (flagSwap) {
                        movieLikedIb.setBackgroundResource(R.drawable.notliked)
                    }
                    else {
                        movieLikedIb.setBackgroundResource(R.drawable.liked)
                    }
                    flagSwap = !flagSwap
                    movieDetailViewModel.onLikePressed(it)
                }

            })
        }
    }

    private val observerTrailer = Observer<TrailerResponseDto> {
        with(viewBinding){
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(it.trailers[0].key , 0F)
                }
            })
        }
    }

    override fun onBackPressed(): Boolean {
        movieDetailViewModel.onBackPressed()
        return true
    }

    companion object {
        fun getInstance(name: String, movieId: Int): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                    putInt(Constants.MOVIE_ID, movieId)
                }
            }
        }
    }
}