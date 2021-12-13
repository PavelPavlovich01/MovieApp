package com.example.movieapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.network.BuildConfig

class MoviePagingAdapter : PagingDataAdapter<MovieDvo, RecyclerView.ViewHolder>(DataDifferntiator) {

    companion object{
        const val LOADING_LAYOUT = 0
        const val LIST_LAYOUT = 1
    }

    private lateinit var  myOnItemClickLister: OnItemClickListener
    private val movies = ArrayList<MovieDvo>()

    interface OnItemClickListener {
        fun onItemClicked(movieId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myOnItemClickLister = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var poster: ImageView
        var title: TextView
        var description: TextView
        var rating: TextView

        init {
            poster = view.findViewById(R.id.movie_poster_iv)
            title = view.findViewById(R.id.movie_title_tv)
            description = view.findViewById(R.id.movie_description_tv)
            rating = view.findViewById(R.id.movie_rating_tv)
        }

        @SuppressLint("SetTextI18n")
        fun bindItem(movie: MovieDvo){
            Glide.with(poster.context).load(BuildConfig.BASE_POSTER_URL + movie.posterPath).into(poster)
            title.text = movie.title
            description.text = movie.overview
            rating.text = /*resources.getString(R.string.rating) +*/"Rating: " + movie.voteAverage.toString()
        }
    }

    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun bindLoad() {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LIST_LAYOUT -> {
                ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_cardview, parent, false))
            }
            LOADING_LAYOUT -> {
                LoadStateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = getItem(position)
        when (holder.itemViewType) {
            LIST_LAYOUT -> {
                (holder as ViewHolder).apply {
                    bindItem(element!!)
                    movies.add(element)
                    itemView.setOnClickListener {
                        myOnItemClickLister.onItemClicked(element.movieId!!)
                    }
                }
            }

            LOADING_LAYOUT -> {
                (holder as LoadStateViewHolder).apply {
                    bindLoad()
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies.size == 0){
            LIST_LAYOUT
        } else {
            LIST_LAYOUT
        }
    }

    object DataDifferntiator : DiffUtil.ItemCallback<MovieDvo>() {

        override fun areItemsTheSame(oldItem: MovieDvo, newItem: MovieDvo): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: MovieDvo, newItem: MovieDvo): Boolean {
            return oldItem == newItem
        }
    }

}