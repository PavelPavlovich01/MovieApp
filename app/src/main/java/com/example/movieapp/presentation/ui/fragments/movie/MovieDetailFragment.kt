package com.example.movieapp.presentation.ui.fragments.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.network.BuildConfig.BASE_POSTER_URL
import com.example.movieapp.databinding.MovieDetailFragmentBinding
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.viewmodels.movie.MovieDetailViewModel
import com.example.movieapp.util.Constants
import com.example.movieapp.util.State
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
    private var url: String? = null

    private val movieDetailViewModel: MovieDetailViewModel by viewModel{
        parametersOf(
            requireArguments().getInt(Constants.MOVIE_ID),
            (parentFragment as RouterProvider).router
        )
    }

    private val viewBinding by viewBinding<MovieDetailFragmentBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        movieDetailViewModel.getMovieDetails()
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieDetailViewModel.detailMovie.observe(viewLifecycleOwner, { state ->
            with(viewBinding) {
                when (state) {
                    is State.Loading -> {
                        detailsLayout.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is State.Success -> {
                        detailsLayout.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        observerDetails(state.data)
                    }
                    is State.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        movieDetailViewModel.trailerMovie.observe(viewLifecycleOwner, {state ->
            when(state){
                is State.Success -> {
                    if(state.data.trailers.isNotEmpty()){
                        observerTrailer(state.data.trailers[0].key)
                    } else {
                        Toast.makeText(context, "No trailer for that movie", Toast.LENGTH_SHORT).show()
                    }
                }
                is State.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, getString(R.string.chooserTitle)))
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun observerDetails(it: MovieDvo) {
        with(viewBinding){
            movieTitleTv.text = it.title
            movieDescriptionTv.text = it.overview
            movieDateTv.text = context?.resources?.getString(R.string.date) + it.releaseDate
            movieRatingTv.text = context?.resources?.getString(R.string.rating) + it.voteAverage.toString()
            if(it.genres != null && it.genres!!.isNotEmpty()){
                var genres = ""
                for (name in it.genres!!) genres = genres + name.name + ", "
                movieGenresTv.text = context?.resources?.getString(R.string.genres) + genres.subSequence(
                        0,
                        genres.length - 2
                )
            }
            movieBudgetTv.text = context?.resources?.getString(R.string.budget) + it.budget.toString() + "$"
            movieRuntimeTv.text = context?.resources?.getString(R.string.runtime) + it.runtime.toString() + " min."
            Glide.with(this@MovieDetailFragment).load(BASE_POSTER_URL + it.posterPath).into(moviePosterIv)
            if(it.liked != null) {
                movieLikedIb.isSelected = true
                flagSwap = true
            }
            url = it.homepage

            movieLikedIb.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    movieLikedIb.isSelected = !flagSwap
                    flagSwap = !flagSwap
                    movieDetailViewModel.onLikePressed(it)
                }
            })
        }
    }

    private fun observerTrailer(key: String){
        with(viewBinding){
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(key, 0F)
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