package com.example.movieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.network.BuildConfig.BASE_POSTER_URL
import com.example.movieapp.databinding.MovieCardviewBinding

class MovieAdapter : ListAdapter<MovieDvo, MovieAdapter.ViewHolder>(DIFF_UTIL){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        return ViewHolder(MovieCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: MovieCardviewBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        fun bind (item: MovieDvo) = with(binding){
            Glide.with(moviePosterIv.context).load(BASE_POSTER_URL + item.posterPath).into(moviePosterIv)
            movieTitleTv.text = item.title
            movieDescriptionTv.text = item.overview
            movieRatingTv.text = item.voteAverage.toString()
            binding.root.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(v: View) {
            myOnItemClickLister.onItemClicked(v, currentList[position].movieId!!)
        }
    }

    private lateinit var  myOnItemClickLister: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(view: View, movieId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myOnItemClickLister = listener
    }

    companion object {
        private val DIFF_UTIL  = object : DiffUtil.ItemCallback<MovieDvo>() {
            override fun areItemsTheSame(oldItem: MovieDvo, newItem: MovieDvo): Boolean = oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: MovieDvo, newItem: MovieDvo): Boolean = oldItem == newItem
        }
    }
}
