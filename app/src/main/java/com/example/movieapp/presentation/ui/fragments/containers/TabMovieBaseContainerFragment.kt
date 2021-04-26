package com.example.movieapp.presentation.ui.fragments.containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.Screens
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.util.Constants
import com.github.terrakok.cicerone.Back

class TabMovieBaseContainerFragment : Fragment(), BackButtonListener {
    private lateinit var bottomNavigationBar: BottomNavigationBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_tab_movie_base_container, container, false)
        bottomNavigationBar = root.findViewById(R.id.bottom_movie_nav_bar)

        initBottomBar()
        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(0, true)
        }
        return root
    }

    private fun initBottomBar(){
        bottomNavigationBar.addItem(
            BottomNavigationItem(
                R.drawable.coming_soon_icon, R.string.coming_soon_movies
            )
        )

        bottomNavigationBar.addItem(
            BottomNavigationItem(
                R.drawable.popular_icon, R.string.popular_movies
            )
        )

        bottomNavigationBar.addItem(
            BottomNavigationItem(
                R.drawable.favourite_icon, R.string.favourite_movies
            )
        )
            .initialise()

        bottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
            override fun onTabSelected(position: Int) {
                when(position){
                    0 -> selectTab(Constants.UPCOMING_TAB)
                    1 -> selectTab(Constants.POPULAR_TAB)
                    2 -> selectTab(Constants.FAVOURITE_TAB)
                }
            }

            override fun onTabUnselected(position: Int) {}

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }

        })
    }

    private fun selectTab(tab: String){
        val fm = childFragmentManager
        var currentFragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = fm.findFragmentByTag(tab)
        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return
        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(
                R.id.fragment_movies_container_view,
                Screens.tabBaseMovie(tab).createFragment(fm.fragmentFactory), tab
            )
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }

    override fun onBackPressed(): Boolean {
        val fm = childFragmentManager
        var fragment: Fragment? = null
        for(fr in fm.fragments){
            if(fr.isVisible)
                fragment = fr
        }
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
            TabMovieBaseContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, tabName)
                }
            }
    }
}