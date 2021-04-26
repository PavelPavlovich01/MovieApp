package com.example.movieapp.presentation.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.fragments.movie.MoviePopularFragment
import com.example.movieapp.util.Constants

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object{
        fun getInstance(name: String): SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}