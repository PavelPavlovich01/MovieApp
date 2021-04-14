package com.example.movieapp.presentation.ui.fragments.containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.movieapp.R
import com.example.movieapp.presentation.util.Screens
import org.koin.android.ext.android.get

class TabMovieBaseContainerFragment : Fragment() {
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
                    0 -> selectTab("COMING")
                    1 -> selectTab("POPULAR")
                    2 -> selectTab("FAVOURITE")
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

    companion object {
        private const val CONTAINER_NAME = "container_name"

        fun newInstance(tabName: String) =
            TabMovieBaseContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(CONTAINER_NAME, tabName)
                }
            }
    }
}