package com.example.movieapp.presentation.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSettingsBinding
import com.example.movieapp.util.Constants

class SettingsFragment : Fragment() {
    private val settingViewBinding by viewBinding<FragmentSettingsBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object{
        fun getInstance(name: String): SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}