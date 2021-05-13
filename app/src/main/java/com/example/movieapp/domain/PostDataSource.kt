package com.example.movieapp.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.dto.MovieDto
import com.example.movieapp.data.model.dto.MovieResponseDto
import com.example.movieapp.data.model.dto.toMovieDvo
import com.example.movieapp.data.model.dvo.MovieDvo
import com.example.movieapp.data.network.MovieApi
import com.example.movieapp.data.repository.MovieDtoRepository

class PostDataSource(private val movieApi: MovieDtoRepository) : PagingSource<Int, MovieDvo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,  MovieDvo> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = movieApi.getPopularMovies(currentLoadingPageKey)
            val responseData = mutableListOf<MovieDvo>()
            val data = response.movies?.map { it.toMovieDvo() } ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
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