package com.example.movieapp.presentation.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.fragments.movie.MoviePopularFragment
import com.example.movieapp.util.Constants

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object{
        fun getInstance(name: String): ProfileFragment {
            return ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}