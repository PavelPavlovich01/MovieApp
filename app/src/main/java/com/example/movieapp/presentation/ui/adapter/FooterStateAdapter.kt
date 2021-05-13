package com.example.movieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R

class FooterStateAdapter() : LoadStateAdapter<FooterStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading, parent, false)
    ) {

        private val progressBar: ProgressBar = parent.findViewById(R.id.progress_bar)

        fun bind(loadState: LoadState) {
            if(loadState == LoadState.Loading){
                progressBar.isVisible = loadState is LoadState.Loading
            } else {
                progressBar.isVisible = loadState is LoadState.NotLoading
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ) = LoadStateViewHolder(parent)

    override fun onBindViewHolder(
            holder: LoadStateViewHolder,
            loadState: LoadState
    ) = holder.bind(loadState)
}