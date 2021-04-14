package com.example.movieapp.presentation.ui.fragments.containers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R

class TabSettingsContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_settings_container, container, false)
    }

    companion object {
        private const val CONTAINER_NAME = "container_name"

        fun newInstance(tabName: String) =
            TabSettingsContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(CONTAINER_NAME, tabName)
                }
            }
    }
}