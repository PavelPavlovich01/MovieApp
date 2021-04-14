package com.example.movieapp.presentation.navigation

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class LocalCiceroneHolder : Application() {
    private val containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containerTag: String): Cicerone<Router> =
            containers.getOrPut(containerTag) {
                Cicerone.create()
            }
}