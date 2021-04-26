package com.example.movieapp.presentation.ui

import com.example.movieapp.presentation.ui.fragments.containers.*
import com.example.movieapp.presentation.ui.fragments.movie.MovieComingSoonFragment
import com.example.movieapp.presentation.ui.fragments.movie.MovieDetailFragment
import com.example.movieapp.presentation.ui.fragments.movie.MovieFavouriteFragment
import com.example.movieapp.presentation.ui.fragments.movie.MoviePopularFragment
import com.example.movieapp.presentation.ui.fragments.profile.ProfileFragment
import com.example.movieapp.presentation.ui.fragments.settings.SettingsFragment
import com.example.movieapp.util.Constants
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun upcoming(containerName: String) = FragmentScreen {
        MovieComingSoonFragment.getInstance(containerName)
    }

    fun favourite(containerName: String) = FragmentScreen {
        MovieFavouriteFragment.getInstance(containerName)
    }

    fun popular(containerName: String) = FragmentScreen {
        MoviePopularFragment.getInstance(containerName)
    }

    fun profile(containerName: String) = FragmentScreen {
        ProfileFragment.getInstance(containerName)
    }

    fun settings(containerName: String) = FragmentScreen {
        SettingsFragment.getInstance(containerName)
    }

    fun movieDetail(containerName: String, movieId: Int) = FragmentScreen {
        MovieDetailFragment.getInstance(containerName, movieId)
    }

    fun tab(tabName: String) = FragmentScreen {
        when(tabName){
            Constants.MOVIES_TAB -> TabMovieBaseContainerFragment.getInstance(tabName)
            Constants.PROFILE_TAB -> TabProfileContainerFragment.getInstance(tabName)
            Constants.SETTINGS_TAB -> TabSettingsContainerFragment.getInstance(tabName)
            else -> TODO()
        }
    }

    fun tabBaseMovie(tabName: String) = FragmentScreen {
        when(tabName){
            Constants.UPCOMING_TAB -> TabComingSoonContainerFragment.getInstance(tabName)
            Constants.POPULAR_TAB -> TabPopularContainerFragment.getInstance(tabName)
            Constants.FAVOURITE_TAB -> TabFavouriteContainerFragment.getInstance(tabName)
            else -> TODO()
        }
    }
}