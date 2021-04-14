package com.example.movieapp.presentation.util

import com.example.movieapp.presentation.ui.fragments.containers.*
import com.example.movieapp.presentation.ui.fragments.movie.MovieComingSoonFragment
import com.example.movieapp.presentation.ui.fragments.movie.MovieFavouriteFragment
import com.example.movieapp.presentation.ui.fragments.movie.MoviePopularFragment
import com.example.movieapp.presentation.ui.fragments.profile.ProfileFragment
import com.example.movieapp.presentation.ui.fragments.settings.SettingsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun upcoming(containerName: String) = FragmentScreen {
        MovieComingSoonFragment.getNewInstance(containerName)
    }

    fun favourite(containerName: String) = FragmentScreen {
        MovieFavouriteFragment.getNewInstance(containerName)
    }

    fun popular(containerName: String) = FragmentScreen {
        MoviePopularFragment.getNewInstance(containerName)
    }

    fun profile(containerName: String) = FragmentScreen {
        ProfileFragment.getNewInstance(containerName)
    }

    fun settings(containerName: String) = FragmentScreen {
        SettingsFragment.getNewInstance(containerName)
    }

    fun tab(tabName: String) = FragmentScreen {
        when(tabName){
            "MOVIES" -> TabMovieBaseContainerFragment.newInstance(tabName)
            "PROFILE" -> TabProfileContainerFragment.newInstance(tabName)
            "SETTINGS" -> TabSettingsContainerFragment.newInstance(tabName)
            else -> TODO()
        }
    }

    fun tabBaseMovie(tabName: String) = FragmentScreen {
        when(tabName){
            "COMING" -> TabComingSoonContainerFragment.newInstance(tabName)
            "POPULAR" -> TabPopularContainerFragment.newInstance(tabName)
            "FAVOURITE" -> TabFavouriteContainerFragment.newInstance(tabName)
            else -> TODO()
        }
    }
}