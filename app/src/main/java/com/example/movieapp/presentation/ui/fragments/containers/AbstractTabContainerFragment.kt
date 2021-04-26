package com.example.movieapp.presentation.ui.fragments.containers

import androidx.fragment.app.Fragment
import com.example.movieapp.presentation.navigation.LocalCiceroneHolder
import com.example.movieapp.presentation.ui.Screens
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

abstract class AbstractTabContainerFragment : Fragment(), BackButtonListener, RouterProvider {
    abstract val navigator: Navigator

    abstract val containerName: String

    abstract val cicerone: Cicerone<Router>

    val ciceroneHolder: LocalCiceroneHolder by inject()

    override val router: Router
        get() = cicerone.router

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }
}