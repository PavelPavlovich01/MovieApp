package com.example.movieapp.presentation.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.model.dto.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.repository.remote.MovieDtoRepository
import com.example.movieapp.util.Constants
import retrofit2.HttpException

//TODO("response fix")
class PostDataSource(private val movieApi: MovieDtoRepository, private val responseOwner: String) : PagingSource<Int, MovieDvo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,  MovieDvo> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            lateinit var response: MovieResponseDto
            //fix needed
            if(responseOwner.equals(Constants.POPULAR_TAB)){
                response = movieApi.getPopularMovies(currentLoadingPageKey)
            } else {
                response = movieApi.getUpcomingMovies(currentLoadingPageKey)
            }
            val responseData = mutableListOf<MovieDvo>()
            val data = response.movies?.map { it.toMovieDvo() } ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDvo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}