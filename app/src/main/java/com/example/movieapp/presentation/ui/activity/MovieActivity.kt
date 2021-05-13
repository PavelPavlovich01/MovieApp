package com.example.movieapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.movieapp.R
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.presentation.ui.Screens.tab
import com.example.movieapp.util.Constants
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class MovieActivity : AppCompatActivity(), RouterProvider {

    override val router: Router by inject()

    private lateinit var bottomNavigationBar: BottomNavigationBar
    private lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        bottomNavigationBar = findViewById(R.id.bottom_main_nav_bar)
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)

        initBottomBar()
        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(0, true)
        }
    }

    private fun initBottomBar(){
        bottomNavigationBar.addItem(
            BottomNavigationItem(
                R.drawable.movie, R.string.movie
            )
        )

        bottomNavigationBar.addItem(
            BottomNavigationItem(
                R.drawable.search, R.string.search
            )
        )

        bottomNavigationBar.addItem(
            BottomNavigationItem(
                R.drawable.settings, R.string.settings
            )
        )
            .initialise()

        bottomNavigationBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener{
            override fun onTabSelected(position: Int) {
                when(position){
                    0 -> selectTab(Constants.MOVIES_TAB)
                    1 -> selectTab(Constants.SEARCH_TAB)
                    2 -> selectTab(Constants.SETTINGS_TAB)
                }
            }

            override fun onTabUnselected(position: Int) {}

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }
        })
    }

    private fun selectTab(tab: String) {
        val fm = supportFragmentManager
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
                R.id.fragment_main_container_view,
                tab(tab).createFragment(fm.fragmentFactory), tab
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

    override fun onBackPressed(){
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            router.exit()
        }
    }
}