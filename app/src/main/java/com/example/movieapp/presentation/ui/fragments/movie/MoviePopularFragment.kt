package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R

class MoviePopularFragment : MovieBaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_movie_popular, container, false)
    }

    companion object{
        fun newInstance() = MoviePopularFragment()
    }
}