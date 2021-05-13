package com.example.movieapp

import android.app.Application
import com.example.movieapp.data.database.databaseModule
import com.example.movieapp.data.network.networkModule
import com.example.movieapp.data.repository.movieDboBModule
import com.example.movieapp.data.repository.movieDtoModule
import com.example.movieapp.presentation.navigation.ciceroneModule
import com.example.movieapp.presentation.ui.fragments.movie.fragmentComingSoonModule
import com.example.movieapp.presentation.ui.fragments.movie.fragmentDetailModule
import com.example.movieapp.presentation.ui.fragments.movie.fragmentFavouriteModule
import com.example.movieapp.presentation.ui.fragments.movie.fragmentPopularModule
import com.example.movieapp.presentation.ui.fragments.search.fragmentSearchModule
import com.example.movieapp.presentation.ui.fragments.search.searchMovieViewModelModule
import com.example.movieapp.presentation.ui.viewmodels.movie.detailMovieViewModelModule
import com.example.movieapp.presentation.ui.viewmodels.movie.favouriteMovieViewModelModule
import com.example.movieapp.presentation.ui.viewmodels.movie.popularMovieViewModelModule
import com.example.movieapp.presentation.ui.viewmodels.movie.upcomingMovieViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(
                    networkModule,
                    databaseModule,

                    movieDtoModule,
                    movieDboBModule,

                    ciceroneModule,

                    fragmentComingSoonModule,
                    fragmentFavouriteModule,
                    fragmentPopularModule,
                    fragmentDetailModule,
                    fragmentSearchModule,

                    upcomingMovieViewModelModule,
                    favouriteMovieViewModelModule,
                    popularMovieViewModelModule,
                    detailMovieViewModelModule,
                    searchMovieViewModelModule
            )
        }
    }
}