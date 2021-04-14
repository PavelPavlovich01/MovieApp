package com.example.movieapp.presentation.util

import com.example.movieapp.presentation.ui.fragments.containers.*
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
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