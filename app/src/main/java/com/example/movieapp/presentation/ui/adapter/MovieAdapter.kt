package com.example.movieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.dto.MovieDto
import com.example.movieapp.data.network.BuildConfig.BASE_POSTER_URL

class MovieAdapter(private val context: FragmentActivity, private val movies: List<MovieDto>): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    private lateinit var  myOnItemClickLister: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(view: View, movieId: Int);
    }

        fun setOnItemClickListener(listener: OnItemClickListener) {
        myOnItemClickLister = listener;
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
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

        override fun onClick(v: View) {
            val id = movies[this.position].movieId!!
            myOnItemClickLister.onItemClicked(v, id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_cardview, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(BASE_POSTER_URL + movies[position].posterPath).into(holder.poster)
        holder.title.text = movies[position].title
        holder.description.text = movies[position].overview
        holder.rating.text = context.resources.getString(R.string.rating) + movies[position].voteAverage.toString()
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
