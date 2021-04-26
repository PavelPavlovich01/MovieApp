package com.example.movieapp.presentation.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    abstract fun bind(item: T)
}