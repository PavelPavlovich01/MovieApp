package com.example.movieapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.network.BuildConfig.BASE_POSTER_URL

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private lateinit var  myOnItemClickLister: OnItemClickListener
    private val movies = ArrayList<MovieDvo>()

    interface OnItemClickListener {
        fun onItemClicked(view: View, movieId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myOnItemClickLister = listener
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        var poster: ImageView
        var title: TextView
        var description: TextView
        var rating: TextView

        init {
            poster = view.findViewById(R.id.movie_poster_iv)
            title = view.findViewById(R.id.movie_title_tv)
            description = view.findViewById(R.id.movie_description_tv)
            rating = view.findViewById(R.id.movie_rating_tv)
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: MovieDvo){
            Glide.with(poster.context).load(BASE_POSTER_URL + movie.posterPath).into(poster)
            title.text = movie.title
            description.text = movie.overview
            rating.text = /*resources.getString(R.string.rating) +*/"Rating: " + movie.voteAverage.toString()
        }

        override fun onClick(v: View) {
            val id = movies[this.position].movieId!!
            myOnItemClickLister.onItemClicked(v, id)
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun showLoadingView() {
            progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_ITEM){
            MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_cardview, parent, false))
        } else {
            LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bindItems(movies[position])
        } else if (holder is LoadingViewHolder) {
            holder.showLoadingView()
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(newMoviesList: List<MovieDvo>?) {
        if (newMoviesList != null) {
            movies.clear()
            movies.addAll(newMoviesList)
        }
        notifyDataSetChanged()
    }

    fun addData(newMoviesList: List<MovieDvo>?) {
        if (newMoviesList != null) {
            movies.addAll(newMoviesList)
        }
        notifyDataSetChanged()
    }
}
