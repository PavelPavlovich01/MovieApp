package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movieapp.R

class MovieFavouriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favourite, container, false)
    }

    companion object{
        private const val CONTAINER_NAME = "container_name"

        fun getNewInstance(name: String): MovieFavouriteFragment {
            return MovieFavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(CONTAINER_NAME, name)
                }
            }
        }
    }
}