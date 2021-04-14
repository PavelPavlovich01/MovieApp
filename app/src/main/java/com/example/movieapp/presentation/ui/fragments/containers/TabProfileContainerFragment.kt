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
import com.example.movieapp.presentation.util.Screens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class TabProfileContainerFragment : Fragment(), RouterProvider, BackButtonListener {

    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.fragment_container_profile, childFragmentManager)
    }

    private val ciceroneHolder: LocalCiceroneHolder by inject()

    private val containerName: String
        get() = requireArguments().getString(CONTAINER_NAME).toString()

    private val cicerone: Cicerone<Router>
        get() = containerName.let { ciceroneHolder.getCicerone(it) }

    override val router: Router
        get() = cicerone.router

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_profile_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.fragment_container_profile) == null) {
            router.replaceScreen(Screens.profile(containerName))
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.fragment_container_profile)
        return if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()) {
            true
        } else {
            (activity as RouterProvider?)!!.router.exit()
            true
        }
    }

    companion object {
        private const val CONTAINER_NAME = "container_name"

        fun newInstance(tabName: String) =
            TabProfileContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(CONTAINER_NAME, tabName)
                }
            }
    }
}