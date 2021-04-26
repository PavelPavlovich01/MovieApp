package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.dbo.GenreDbo
import com.example.movieapp.data.model.dbo.MovieDbo
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(androidApplication(), AppDataBase::class.java, "movie-db").build() }
    single { get<AppDataBase>().movieDao() }
    single { get<AppDataBase>().genreDao() }
}

@Database(entities = [MovieDbo::class, GenreDbo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}