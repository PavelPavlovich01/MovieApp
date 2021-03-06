package com.example.movieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ListItemLoadingBinding
import com.example.movieapp.databinding.ListItemLoadingErrorBinding

class FooterStateAdapter() : LoadStateAdapter<FooterStateAdapter.ItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {

        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder internal constructor(private val binding: ListItemLoadingBinding) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            // Do nothing
        }

        companion object {
            operator fun invoke(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup? = null,
                    attachToRoot: Boolean = false
            ): ProgressViewHolder {
                return ProgressViewHolder(ListItemLoadingBinding.inflate(layoutInflater, parent, attachToRoot))
            }
        }
    }

    class ErrorViewHolder internal constructor(
            private val binding: ListItemLoadingErrorBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
        }

        companion object {
            operator fun invoke(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup? = null,
                    attachToRoot: Boolean = false
            ): ErrorViewHolder {
                return ErrorViewHolder(
                        ListItemLoadingErrorBinding.inflate(layoutInflater, parent, attachToRoot)
                )
            }
        }
    }
}