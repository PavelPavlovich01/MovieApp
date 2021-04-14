package com.example.movieapp.presentation.ui.fragments.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.github.terrakok.cicerone.Router
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

open class MovieBaseFragment : Fragment() {
    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val textView = activity?.findViewById<TextView>(R.id.textView)

        val tabLayout: TabLayout? = view.findViewById(R.id.tab_layout)

        //tabLayout?.selectTab(tabLayout.getTabAt(0))

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> TODO()
                    1 -> TODO()
                    2 -> TODO()

                    /*R.id.tab_coming_soon -> {router.navigateTo(Screens.MovieComingSoonScreen())}
                    R.id.tab_popular -> {router.navigateTo(Screens.MoviePopularScreen())}
                    R.id.tab_favourite -> {TODO("tab to favourite activity")}*/
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //tabLayout.selectTab(tabLayout.getTabAt(0))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }
        })
    }

    companion object{
        fun getInstance(): MovieBaseFragment {
            return MovieBaseFragment()
        }
    }
}