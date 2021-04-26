package com.example.movieapp.presentation.ui.fragments.containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.presentation.navigation.LocalCiceroneHolder
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.Screens
import com.example.movieapp.util.Constants
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class TabSettingsContainerFragment : AbstractTabContainerFragment(){
    override val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.fragment_container_settings, childFragmentManager)
    }

    override val containerName: String
        get() = requireArguments().getString(Constants.CONTAINER_NAME).toString()

    override val cicerone: Cicerone<Router>
        get() = containerName.let { ciceroneHolder.getCicerone(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_settings_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.fragment_container_settings) == null) {
            router.replaceScreen(Screens.settings(containerName))
        }
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.fragment_container_settings)
        return if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()) {
            true
        } else {
            (activity as RouterProvider?)!!.router.exit()
            true
        }
    }

    companion object {
        fun getInstance(tabName: String) =
            TabSettingsContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, tabName)
                }
            }
    }
}