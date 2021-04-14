package com.example.movieapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R

class PagerAdapter(private val context: FragmentActivity?, private val words: List<String>): RecyclerView.Adapter<PagerAdapter.PageHolder>(){
    inner class PageHolder(view: View): RecyclerView.ViewHolder(view){
        //val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(LayoutInflater.from(context).inflate(R.layout.fragment_movie_popular, parent, false))


    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        //holder.textView.text = words[position]
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}