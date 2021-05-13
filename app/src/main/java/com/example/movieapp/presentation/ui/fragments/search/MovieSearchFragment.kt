package com.example.movieapp.presentation.ui.fragments.search

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movieapp.R
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.movieapp.presentation.ui.common.BackButtonListener
import com.example.movieapp.presentation.ui.common.RouterProvider
import com.example.movieapp.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val fragmentSearchModule = module {
    factory { MovieSearchFragment() }
}

class MovieSearchFragment : Fragment(), MovieAdapter.OnItemClickListener, BackButtonListener {
    private val movieSearchViewModel by viewModel<MovieSearchViewModel> {
        parametersOf((parentFragment as RouterProvider).router)
    }

    private val viewBinding by viewBinding<FragmentSearchBinding>()

    private lateinit var myAdapter: MovieAdapter

    private val observer = Observer<List<MovieDvo>?> {
        with(viewBinding){
            searchRecyclerview.setHasFixedSize(false)
            myAdapter = MovieAdapter()
            myAdapter.setData(it)
            myAdapter.setOnItemClickListener(this@MovieSearchFragment)
            searchRecyclerview.adapter = myAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                movieSearchViewModel.searchMovies(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieSearchViewModel.moviesLiveData.observe(viewLifecycleOwner, observer)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onItemClicked(view: View, movieId: Int) {
        movieSearchViewModel.onItemClicked(movieId)
    }

    override fun onBackPressed(): Boolean {
        movieSearchViewModel.onBackPressed()
        return true
    }

    companion object{
        fun getInstance(name: String): MovieSearchFragment {
            return MovieSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CONTAINER_NAME, name)
                }
            }
        }
    }
}